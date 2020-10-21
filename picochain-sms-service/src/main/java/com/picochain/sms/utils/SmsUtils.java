package com.picochain.sms.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.picochain.sms.config.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author mafeng
 */
@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {

    private final SmsProperties prop;
    private final StringRedisTemplate redisTemplate;

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private static final String PRODUCT = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String KEY_PREFIX = "sms:phone";

    private static final long SMS_MIN_DURATION = 30000;

    @Autowired
    public SmsUtils(SmsProperties prop, StringRedisTemplate redisTemplate) {
        this.prop = prop;
        this.redisTemplate = redisTemplate;
    }

    public SendSmsResponse sendSms(String phone, String code, String signName, String templateCode, String templateParam) throws ClientException {
        // 按照手机号设置redis中存储的key值
        String key = KEY_PREFIX + phone;

        // 按照手机号限流
        String startTime = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(startTime)) {
            long start = Long.parseLong(startTime);
            if (System.currentTimeMillis() - start < SMS_MIN_DURATION) {
                return null;
            }
        }

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                prop.getAccessKeyId(), prop.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        if (!"OK".equals(sendSmsResponse.getCode())) {
            log.error("[短信服务] 发送短信失败，phone：{}, 原因：{}", phone, sendSmsResponse.getMessage());
        }

        // 发送短信成功，记录一下发送时间，并制定生存时间
        redisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()), 5, TimeUnit.MINUTES);

        return sendSmsResponse;
    }
}
