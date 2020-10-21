package com.picochain.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mafeng
 * @date 2020/6/27 22:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionEnum {
    /**
     * 用户保存失败
     */
    USER_SAVE_ERROR(400, "用户保存失败"),
    TOKEN_GENERATE_FAILED(500, "token创建失败"),
    UN_AUTHORIZED(403, "未授权"),
    FILE_UPLOAD_FAILED(500, "文件上传失败"),
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    ;
    private int id;
    private String msg;

}
