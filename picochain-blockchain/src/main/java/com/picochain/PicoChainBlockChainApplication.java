package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mafeng
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PicoChainBlockChainApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainBlockChainApplication.class);
    }
}
