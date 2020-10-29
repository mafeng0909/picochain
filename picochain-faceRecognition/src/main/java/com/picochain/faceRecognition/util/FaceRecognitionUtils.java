package com.picochain.faceRecognition.util;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.enums.ImageFormat;
import com.arcsoft.face.toolkit.ImageInfo;
import com.picochain.faceRecognition.config.FaceRecognitionProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @author mafeng
 * @data 2020/10/19
 **/
@Slf4j
@Component
@EnableConfigurationProperties(FaceRecognitionProperties.class)
public class FaceRecognitionUtils {

    private final FaceRecognitionProperties props;
    private final Logger logger = LoggerFactory.getLogger(FaceRecognitionUtils.class);
    private final FaceEngine faceEngine;

    @Autowired
    public FaceRecognitionUtils(FaceRecognitionProperties props) {
        this.props = props;
        this.faceEngine = new FaceEngine(props.getFaceEnginePath());
    }

    /**
     * 配置引擎以及初始化
     */
    public void faceEngineConfig() {

        //从官网获取
        String appId = props.getAppId();
        String sdkKey = props.getSdkKey();

        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }

        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
    }

    /**
     * 根据图片提取人脸特征数据
     *
     * @param picturePath
     * @return
     */
    public FaceFeature getFaceFeature(String picturePath) {

        //人脸检测
        ImageInfo imageInfo = getRGBData(new File(picturePath));
        List<FaceInfo> faceInfoList = new ArrayList<>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        logger.debug("{}", faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        logger.debug("特征值大小：{}", faceFeature.getFeatureData().length);

        return faceFeature;
    }

    /**
     * 特征比对
     *
     * @param faceFeature1
     * @param faceFeature2
     * @return
     */
    public float faceEqual(FaceFeature faceFeature1, FaceFeature faceFeature2) {

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature1.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());

        FaceSimilar faceSimilar = new FaceSimilar();
        faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

        logger.info("相似度：{}", faceSimilar.getScore());

        return faceSimilar.getScore();
    }

    /**
     * 引擎卸载
     */
    public void faceEngineClose() {
        faceEngine.unInit();
    }
}
