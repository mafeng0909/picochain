package com.picochain.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

import com.picochain.sms.config.SmsProperties;
import com.picochain.sms.utils.SmsUtils;
import com.picochain.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author mafeng
 */
@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {

    private final SmsUtils smsUtils;
    private final SmsProperties prop;

    @Autowired
    public SmsListener(SmsUtils smsUtils, SmsProperties prop) {
        this.smsUtils = smsUtils;
        this.prop = prop;
    }

    /**
     * 消息监听器
     * @param msg
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "picochain.sms.queue", durable = "true"),
            exchange = @Exchange(
                    value = "picochain.sms.exchange",
                    ignoreDeclarationExceptions = "true"
                ),
            key = {"sms.verify.code"}
        ))
    public void listenSms(Map<String, String> msg) throws Exception {
        if (CollectionUtils.isEmpty(msg)) {
            // 放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            // 放弃处理
            return;
        }
        // 发送消息
        try {
            SendSmsResponse resp = this.smsUtils.sendSms(phone, code,
                    prop.getSignName(), prop.getVerifyCodeTemplate(), JsonUtils.serialize(msg));
        }catch (Exception e) {
            log.error("[短信服务] 发送短信异常，phone：{}", phone, e);
        }
    }
}
