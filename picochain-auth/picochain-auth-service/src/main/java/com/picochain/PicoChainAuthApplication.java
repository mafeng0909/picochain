package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author mafeng
 * @date 2020/7/2 19:47
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PicoChainAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainAuthApplication.class);
    }
}
