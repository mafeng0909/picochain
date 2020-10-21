package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author mafeng
 * @date 2020/6/12 9:14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class PicoChainGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainGatewayApplication.class);
    }
}
