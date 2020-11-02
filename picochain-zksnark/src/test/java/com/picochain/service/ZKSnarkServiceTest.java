package com.picochain.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZKSnarkServiceTest {

    @Autowired
    private ZKSnarkService zkSnarkService;

    @Test
    public void setZkSnarkServiceTest() throws IOException, InterruptedException {
        boolean b = zkSnarkService.generateProof();
        System.out.println(b);
    }
}