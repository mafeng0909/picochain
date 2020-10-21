package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mafeng
 * @date 2020/6/12 10:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.picochain.user.mapper")
public class PicoChainUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainUserApplication.class);
    }
}
