package com.picochain.faceRecognition.service;

import com.arcsoft.face.FaceFeature;
import com.picochain.faceRecognition.config.FaceRecognitionProperties;
import com.picochain.faceRecognition.controller.FaceRecognitionController;
import com.picochain.faceRecognition.pojo.IDCardResult;
import com.picochain.faceRecognition.pojo.KeyDetail;
import com.picochain.faceRecognition.util.FaceRecognitionUtils;
import com.picochain.faceRecognition.util.IDCardRecognitionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author mafeng
 * @data 2020/10/19
 **/
@Service
@EnableConfigurationProperties(FaceRecognitionProperties.class)
public class FaceRecognitionService {

    private final StringRedisTemplate redisTemplate;
    private final FaceRecognitionUtils faceRecognitionUtils;
    private final FaceRecognitionProperties props;
    private final IDCardRecognitionUtil idCardRecognitionUtil;

    private static final Logger logger = LoggerFactory.getLogger(FaceRecognitionController.class);
    private static final String KEY_PREFIX = "face:compare:result";
    private static final String KEY_PREFIX_IDCARD = "face:compare:result";
    private static String path1 = "";
    private static String path2 = "";
    private static Integer count = 0;
    private static boolean flagOfIdCard = false;


    @Autowired
    public FaceRecognitionService(StringRedisTemplate redisTemplate, FaceRecognitionUtils faceRecognitionUtils, FaceRecognitionProperties props, IDCardRecognitionUtil idCardRecognitionUtil) {
        this.redisTemplate = redisTemplate;
        this.faceRecognitionUtils = faceRecognitionUtils;
        this.props = props;
        this.idCardRecognitionUtil = idCardRecognitionUtil;
    }

    /**
     * 验证人脸数据是否通过
     *
     * @return
     */
    public boolean verifyFace() {
        // 1、配置引擎
        faceRecognitionUtils.faceEngineConfig();

        // 2、获取人脸特征数据
        if (count < 2) {
            return false;
        }
        FaceFeature faceFeature1 = faceRecognitionUtils.getFaceFeature(path1);
        FaceFeature faceFeature2 = faceRecognitionUtils.getFaceFeature(path2);

        // 3、进行比对
        float v = faceRecognitionUtils.faceEqual(faceFeature1, faceFeature2);

        // 4、关闭引擎
        faceRecognitionUtils.faceEngineClose();

        // 5、存储比对结果
        boolean res = v >= props.getThreshold();
        if (res) {
            redisTemplate.opsForValue().set(KEY_PREFIX, String.valueOf(v));
        }

        return res;
    }

    /**
     * 上传图片
     * @param pictureName
     * @return
     */
    public boolean uploadImage(String pictureName) {

        // 判断图片名称是否为空
        if (StringUtils.isEmpty(pictureName)) {
            logger.error("图片为空");
            return false;
        }
        logger.info("图片名称: {}",pictureName);

        count++;
        if (count % 2 != 0) {
            path1 = idCardRecognitionUtil.getImagePath() + pictureName;
            String idCard = getIdCard(path1);
            if (!StringUtils.isEmpty(idCard)) {
                flagOfIdCard = true;
                redisTemplate.opsForValue().set(KEY_PREFIX_IDCARD, idCard);
            }
        }
        if (count % 2 == 0) {
           path2 = idCardRecognitionUtil.getImagePath() + pictureName;
            String idCard = getIdCard(path2);
            if (StringUtils.isEmpty(idCard)) {
                return flagOfIdCard;
            }else {
                if (flagOfIdCard) {
                    return false;
                }
                flagOfIdCard = true;
                redisTemplate.opsForValue().set(KEY_PREFIX_IDCARD, idCard);
            }
        }
        return true;
    }

    /**
     * 从图片中提取身份证号
     *
     * @param path
     * @return
     */
    public String getIdCard(String path) {

        IDCardResult idCardResult = idCardRecognitionUtil.readIdCard(true, true, "front", path);
        KeyDetail keyDetail = idCardResult.getWords_result().get("公民身份号码");

        if (StringUtils.isEmpty(keyDetail)) {
            return null;
        }
        logger.info("公民身份号码: {}", keyDetail.getWords());
        return keyDetail.getWords();
    }

    /**
     * 从redis中提取身份证号
     *
     * @return
     */
    public Integer getIdCard() {

        return Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(KEY_PREFIX_IDCARD)));
    }

}
