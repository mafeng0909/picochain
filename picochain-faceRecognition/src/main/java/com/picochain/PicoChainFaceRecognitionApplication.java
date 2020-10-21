package com.picochain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mafeng
 * @data 2020/10/17
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class PicoChainFaceRecognitionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicoChainFaceRecognitionApplication.class);
    }
}
