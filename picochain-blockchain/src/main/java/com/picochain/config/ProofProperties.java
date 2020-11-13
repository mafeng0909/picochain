package com.picochain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author mafeng
 */
@Data
@Primary
@Component
@ConfigurationProperties(prefix = "picochain.zkp")
public class ProofProperties {

    private String path;
}
