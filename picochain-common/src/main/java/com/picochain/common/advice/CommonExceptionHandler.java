package com.picochain.common.advice;

import com.picochain.common.enums.ExceptionEnum;
import com.picochain.common.exception.PcException;
import com.picochain.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author mafeng
 * @date 2020/6/27 22:10
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(PcException.class)
    public ResponseEntity<ExceptionResult> handleException(PcException e) {
        return ResponseEntity.status(e.getExceptionEnum().getId())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
