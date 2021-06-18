package com.picochain.controller;

import com.picochain.pojo.ShareData;
import com.picochain.service.ShareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mafeng
 */
@RestController
@RequestMapping
public class ShareController {

    private final ShareService shareService;

    private final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @GetMapping("getData")
    public ResponseEntity<List<ShareData>> getShareData() {
        return ResponseEntity.ok(shareService.getData());
    }

}
