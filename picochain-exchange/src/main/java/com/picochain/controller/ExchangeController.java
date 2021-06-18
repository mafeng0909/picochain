package com.picochain.controller;

import com.picochain.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mafeng
 */
@RestController
@RequestMapping
public class ExchangeController {

    private final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @PostMapping("exchangeOrder")
    public ResponseEntity<Boolean> exchangeOrder(@RequestParam("order") String order) {
        logger.info("test into ......");
        return ResponseEntity.ok(exchangeService.exchangeOrder(order));
    }
}
