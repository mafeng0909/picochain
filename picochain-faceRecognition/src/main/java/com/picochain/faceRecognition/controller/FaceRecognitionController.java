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
    public ResponseEntity<Boolean> verifyFace() {
        return ResponseEntity.ok(faceRecognitionService.verifyFace());
    }

    @PostMapping("uploadImage")
    public ResponseEntity<Boolean> uploadImage(@RequestParam("pictureName") String pictureName) {
        return ResponseEntity.ok(faceRecognitionService.uploadImage(pictureName));
    }

    @GetMapping("getIdCard")
    public ResponseEntity<Integer> getIdCard() {
        return ResponseEntity.ok(faceRecognitionService.getIdCard());
    }
}