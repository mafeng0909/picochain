package com.picochain.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author mafeng
 * @date 2020/5/31 21:53
 */
@Primary
@Component
@Data
@ConfigurationProperties(prefix = "picochain.sms")
public class SmsProperties {

    String accessKeyId;
    String accessKeySecret;
    String signName;
    String verifyCodeTemplate;
}
