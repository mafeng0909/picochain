package com.picochain.faceRecognition.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *@author mafeng
 */
@Data
@Component
public class KeyDetail {

    private String words;
    private Location location;

}
