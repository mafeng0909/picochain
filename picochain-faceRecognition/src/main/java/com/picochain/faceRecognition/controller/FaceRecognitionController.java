package com.picochain.faceRecognition.controller;

import com.picochain.faceRecognition.service.FaceRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author mafeng
 * @data 2020/10/19
 **/
@RestController
@RequestMapping
public class FaceRecognitionController {

    private final FaceRecognitionService faceRecognitionService;

    @Autowired
    public FaceRecognitionController(FaceRecognitionService faceRecognitionService) {
        this.faceRecognitionService = faceRecognitionService;
    }

    /**
     * 验证人脸是否匹配
     *
     * @param path1
     * @param path2
     * @return
     */
    @PostMapping("verifyFace")
    public ResponseEntity<Boolean> verifyFace(String path1, String path2) {
        return ResponseEntity.ok(faceRecognitionService.verifyFace(path1, path2));
    }
}
