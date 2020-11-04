package com.picochain.service;

import com.picochain.common.enums.ExceptionEnum;
import com.picochain.common.exception.PcException;
import com.picochain.config.ZKPProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author mafeng
 */
@Service
@EnableConfigurationProperties(ZKPProperties.class)
public class ZKSnarkService {

    private static final String KEY_PREFIX = "face:compare:result";

    private final StringRedisTemplate redisTemplate;
    private final ZKPProperties props;


    @Autowired
    public ZKSnarkService(StringRedisTemplate redisTemplate, ZKPProperties props) {
        this.redisTemplate = redisTemplate;
        this.props = props;
    }

    /**
     * 生成证明
     *
     * @return
     */
    public boolean generateProof() throws IOException, InterruptedException {
        // 1 从redis中提取人脸对比阈值
        String s = redisTemplate.opsForValue().get(KEY_PREFIX);
        assert s != null;
        float v = Float.parseFloat(s);
        String res = String.valueOf(Math.floor(v * 100));

        // 2 生成证明 --> 在特定文件夹下，调用linux命令
        String path = props.getPath();

        Process process = null;

        String threshold = String.valueOf((int) (props.getThreshold() * 100));
        String command = path + "sudoku prove " + path + "pk_data.raw " + threshold + " " + res + " " + path;
        System.out.println(command);
        process = Runtime.getRuntime().exec(command);
        process.waitFor();

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        is.close();
        isr.close();
        br.close();

        // 3 读取证明（通过读取来判断是否生成证明）
        String filePath = path + "proof_data";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new PcException(ExceptionEnum.PROOF_CREATE_FAILED);
        }

        return true;
    }
}
