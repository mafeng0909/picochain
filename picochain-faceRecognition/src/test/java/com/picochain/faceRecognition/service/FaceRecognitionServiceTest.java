package com.picochain.faceRecognition.service;

import com.arcsoft.face.FaceFeature;
import com.picochain.faceRecognition.pojo.IDCardResult;
import com.picochain.faceRecognition.pojo.KeyDetail;
import com.picochain.faceRecognition.util.FaceRecognitionUtils;
import com.picochain.faceRecognition.util.IDCardRecognitionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mafeng
 * @data 2020/10/20
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class FaceRecognitionServiceTest {

    @Autowired
    private FaceRecognitionService faceRecognitionService;

    @Autowired
    private FaceRecognitionUtils faceRecognitionUtils;

    @Autowired
    private IDCardRecognitionUtil idCardRecognitionUtil;

    @Test
    public void setFaceRecognitionServiceTest() {
        String path1 = "/home/mafeng/IdeaProjects/picochain-web/src/picture/000-mafeng.jpg";
        String path2 = "/home/mafeng/IdeaProjects/picochain-web/src/picture/006-mafeng.jpg";
//        boolean b = faceRecognitionService.verifyFace(path1, path2);
//        System.out.println(b);        faceRecognitionUtils.faceEngineConfig();
//        long startTime = System.currentTimeMillis();
        long startTime = System.nanoTime();
        FaceFeature faceFeature1 = faceRecognitionUtils.getFaceFeature(path1);
//        long endTime = System.currentTimeMillis();
        long endTime = System.nanoTime();
        System.out.println("人脸检测特征提取所需时间-1:" + (endTime - startTime) + "ns");

        startTime = System.nanoTime();
        FaceFeature faceFeature2 = faceRecognitionUtils.getFaceFeature(path2);
        endTime = System.nanoTime();
        System.out.println("人脸检测+特征提取所需时间-2:" + (endTime - startTime) + "ms");

        startTime = System.nanoTime();
        float v = faceRecognitionUtils.faceEqual(faceFeature1, faceFeature2);
        endTime = System.nanoTime();
        System.out.println("人脸比对所需时间:" + (endTime - startTime) + "ms");
    }

    @Test
    public void test01() {
        String imagePath = idCardRecognitionUtil.getImagePath();
        IDCardResult idCardResult = idCardRecognitionUtil.readIdCard(true, true, "front", imagePath);
        System.out.println("***********: " + idCardResult);
        System.out.println("***********: " + idCardRecognitionUtil.getMessage(idCardResult));
        KeyDetail keyDetail = idCardResult.getWords_result().get("公民身份号码");
        System.out.println(keyDetail.getWords());
    }
}