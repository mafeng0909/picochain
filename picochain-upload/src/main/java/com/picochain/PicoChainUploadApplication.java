package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mafeng
 * @date 2020/7/3 15:52
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PicoChainUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainUploadApplication.class);
    }
}
