package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mafeng
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PicoChainZkSnarkApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainZkSnarkApplication.class);
    }
}
