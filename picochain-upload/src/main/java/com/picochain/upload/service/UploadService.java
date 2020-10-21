package com.picochain.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.picochain.common.enums.ExceptionEnum;
import com.picochain.common.exception.PcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mafeng
 * @date 2020/7/3 16:51
 */
@Slf4j
@Service
public class UploadService {


//    private final FastFileStorageClient storageClient;

    private static final List<String> ALLOW_TYPE = Arrays.asList("image/jpeg", "image/png", "image/bmp");

//    @Autowired
//    public UploadService(FastFileStorageClient storageClient) {
//        this.storageClient = storageClient;
//    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {

        try {
            // 校验文件类型
            String contentType = file.getContentType();
            System.out.println(contentType);
            if (!ALLOW_TYPE.contains(contentType)) {
                throw new PcException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            // 校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new PcException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            // 保存到服务器
            file.transferTo(new File("F:\\IdeaProjects\\image\\" + file.getOriginalFilename()));
//            String ext = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
//            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);

            // 返回url
            return "http://image.leyou.com/" + file.getOriginalFilename();
//            return "http://image.picochain.com/" + storePath.getFullPath();
        } catch (IOException e) {
            // 上传失败
            log.info("文件上传失败", e);
            throw new PcException(ExceptionEnum.FILE_UPLOAD_FAILED);
        }
    }
}
