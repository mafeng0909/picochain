package com.picochain.faceRecognition.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author mafeng
 */
@Data
@Component
public class Location {

    private Integer top;
    private Integer left;
    private Integer width;
    private Integer height;

}
