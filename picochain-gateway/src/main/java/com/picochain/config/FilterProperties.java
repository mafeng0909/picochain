package com.picochain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mafeng
 */
@Primary
@Data
@Component
@ConfigurationProperties(prefix = "picochain.filter")
public class FilterProperties {

    private List<String> allowPaths;
}
