package com.picochain.faceRecognition.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author mafeng
 */
@Primary
@Data
@Component
@ConfigurationProperties(prefix = "picochain.idcardrecognition")
public class IdCardRecognitionProperties {

    String appId;
    String appKey;
    String secretKey;
    String path;
}
