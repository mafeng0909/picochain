package com.picochain.controller;

import com.picochain.service.ZKSnarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author mafeng
 */
@RestController
@RequestMapping
public class ZKSnarkController {

    private final ZKSnarkService zkSnarkService;

    @Autowired
    public ZKSnarkController(ZKSnarkService zkSnarkService) {
        this.zkSnarkService = zkSnarkService;
    }

    /**
     * 生成证明
     *
     * @return
     */
    @GetMapping("generateProof")
    public ResponseEntity<Boolean> generateProof() throws IOException, InterruptedException {
        return ResponseEntity.ok(zkSnarkService.generateProof());
    }

}
