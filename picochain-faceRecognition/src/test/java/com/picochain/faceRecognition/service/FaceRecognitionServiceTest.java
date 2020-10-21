package com.picochain.faceRecognition.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author mafeng
 * @data 2020/10/20
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class FaceRecognitionServiceTest {

    @Autowired
    private FaceRecognitionService faceRecognitionService;

    @Test
    public void setFaceRecognitionServiceTest() {
        String path1 = "F:\\ArcSoft_ArcFace_Java_Windows_x64_V2.2\\picture\\001-mafeng.jpg";
        String path2 = "F:\\ArcSoft_ArcFace_Java_Windows_x64_V2.2\\picture\\003-mafeng.jpg";
        boolean b = faceRecognitionService.verifyFace(path1, path2);
        System.out.println(b);
    }
}