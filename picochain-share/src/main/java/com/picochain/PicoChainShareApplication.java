package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mafeng
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.picochain.mapper")
public class PicoChainShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainShareApplication.class);
    }
}
