package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author mafeng
 * @date 2020/6/12 9:01
 */
@SpringBootApplication
@EnableEurekaServer
public class PicoChainRegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainRegisterApplication.class);
    }
}
