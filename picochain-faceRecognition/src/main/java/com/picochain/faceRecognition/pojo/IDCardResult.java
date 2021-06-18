package com.picochain.faceRecognition.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author mafeng
 */
@Data
@Component
public class IDCardResult {

    private Long log_id;
    private String image_status;

    private Integer direction;
    private String risk_type;
    private String edit_tool;
    private Map<String,KeyDetail> words_result;
    private Integer words_result_num;

}
