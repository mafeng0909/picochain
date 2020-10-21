package com.picochain.common.vo;

import com.picochain.common.enums.ExceptionEnum;
import lombok.Data;

import java.util.EmptyStackException;

/**
 * @author mafeng
 * @date 2020/6/27 22:58
 */
@Data
public class ExceptionResult {

    private int status;
    private String message;
    private Long timeStamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getId();
        this.message = em.getMsg();
        this.timeStamp = System.currentTimeMillis();
    }
}
