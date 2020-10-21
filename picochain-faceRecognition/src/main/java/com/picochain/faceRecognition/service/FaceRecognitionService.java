package com.picochain.faceRecognition.service;

import com.arcsoft.face.FaceFeature;
import com.picochain.faceRecognition.config.FaceRecognitionProperties;
import com.picochain.faceRecognition.util.FaceRecognitionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author mafeng
 * @data 2020/10/19
 **/
@Service
@EnableConfigurationProperties(FaceRecognitionProperties.class)
public class FaceRecognitionService {

    private final FaceRecognitionUtils faceRecognitionUtils;
    private final FaceRecognitionProperties props;

    @Autowired
    public FaceRecognitionService(FaceRecognitionUtils faceRecognitionUtils, FaceRecognitionProperties props) {
        this.faceRecognitionUtils = faceRecognitionUtils;
        this.props = props;
    }

    /**
     * 验证人脸数据是否通过
     *
     * @param path1
     * @param path2
     * @return
     */
    public boolean verifyFace(String path1, String path2) {
        // 1、配置引擎
        faceRecognitionUtils.faceEngineConfig();

        // 2、获取人脸特征数据
        FaceFeature faceFeature1 = faceRecognitionUtils.getFaceFeature(path1);
        FaceFeature faceFeature2 = faceRecognitionUtils.getFaceFeature(path2);

        // 3、进行比对
        float v = faceRecognitionUtils.faceEqual(faceFeature1, faceFeature2);

        // 4、关闭引擎
        faceRecognitionUtils.faceEngineClose();

        return v >= props.getThreshold();
    }
}
