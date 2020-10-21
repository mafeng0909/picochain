package com.picochain.faceRecognition.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author mafeng
 * @data 2020/10/17
 **/
@Primary
@Component
@Data
@ConfigurationProperties(prefix = "picochain.facerecognition")
public class FaceRecognitionProperties {

    String appId;
    String sdkKey;
    String faceEnginePath;
    Float threshold;

}
