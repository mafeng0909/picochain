package com.picochain.faceRecognition.util;

import com.baidu.aip.ocr.AipOcr;
import com.picochain.faceRecognition.config.IdCardRecognitionProperties;
import com.picochain.faceRecognition.pojo.IDCardResult;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 身份证识别的工具类
 *
 * @author mafeng
 */
@Primary
@Component
@EnableConfigurationProperties(IdCardRecognitionProperties.class)
public class IDCardRecognitionUtil {

    private static final Logger logger = LoggerFactory.getLogger(IDCardRecognitionUtil.class);
    private final IdCardRecognitionProperties props;

    @Autowired
    public IDCardRecognitionUtil(IdCardRecognitionProperties idCardRecognitionProperties) {
        this.props = idCardRecognitionProperties;
    }

    public IDCardResult readIdCard(boolean detectDirectionFlag, boolean detectRiskFlag, String idCardSide, String imagePath) {
        logger.info("APP_ID_IDCARD: {}", props.getAppId());
        logger.info("API_KEY_IDCARD: {}", props.getAppKey());
        logger.info("SECRET_KEY_IDCARD: {}", props.getSecretKey());

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(props.getAppId(), props.getAppKey(), props.getSecretKey());
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "" + detectDirectionFlag);
        options.put("detect_risk", "" + detectRiskFlag);
        if (StringUtils.isEmpty(idCardSide) || (!"front".equals(idCardSide) && !"back".equals(idCardSide))) {
            logger.error("身份证正反面参数idCardSide传入不正确!");
            return null;
        }
        JSONObject res = client.idcard(imagePath, idCardSide, options);
        return parseObject(res.toString(), IDCardResult.class);
    }

    public String getMessage(IDCardResult idCardResult) {
        StringBuilder sb = new StringBuilder();
        //image_status
        if ("normal".equals(idCardResult.getImage_status())) {
            sb.append("识别正常;");
        } else if ("reversed_side".equals(idCardResult.getImage_status())) {
            sb.append("未摆正身份证;");
        } else if ("non_idcard".equals(idCardResult.getImage_status())) {
            sb.append("上传的图片中不包含身份证;");
        } else if ("blurred".equals(idCardResult.getImage_status())) {
            sb.append("身份证模糊;");
        } else if ("over_exposure".equals(idCardResult.getImage_status())) {
            sb.append("身份证关键字段反光或过曝;");
        } else if ("unknown".equals(idCardResult.getImage_status())) {
            sb.append("未知状态;");
        }
        //risk_type
        if (!StringUtils.isEmpty(idCardResult.getRisk_type())) {
            if ("normal".equals(idCardResult.getRisk_type())) {
                sb.append("正常身份证;");
            } else if ("copy".equals(idCardResult.getRisk_type())) {
                sb.append("复印件;");
            } else if ("temporary".equals(idCardResult.getRisk_type())) {
                sb.append("临时身份证;");
            } else if ("screen".equals(idCardResult.getRisk_type())) {
                sb.append("翻拍;");
            } else if ("unknown".equals(idCardResult.getRisk_type())) {
                sb.append("其他未知情况;");
            }
        }
        if (!StringUtils.isEmpty(idCardResult.getEdit_tool())) {
            sb.append("该身份证被图片处理软件").append(idCardResult.getEdit_tool()).append("编辑处理过;");
        }
        return sb.toString();
    }

    public String getImagePath() {
        return props.getPath();
    }
}
