package com.picochain.common.exception;

import com.picochain.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mafeng
 * @date 2020/6/27 22:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PcException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}
