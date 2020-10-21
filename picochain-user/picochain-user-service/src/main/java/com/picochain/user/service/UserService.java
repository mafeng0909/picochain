package com.picochain.user.service;

import com.picochain.common.enums.ExceptionEnum;
import com.picochain.common.exception.PcException;
import com.picochain.common.utils.CodecUtils;
import com.picochain.common.utils.NumberUtils;
import com.picochain.user.mapper.UserMapper;
import com.picochain.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author mafeng
 * @date 2020/6/27 20:48
 */
@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;
    private final AmqpTemplate amqpTemplate;
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:code:phone:";

    @Autowired
    public UserService(UserMapper userMapper, AmqpTemplate amqpTemplate, StringRedisTemplate redisTemplate) {
        this.userMapper = userMapper;
        this.amqpTemplate = amqpTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 检验数据是否可用，即手机号和用户名是否唯一
     * @param data
     * @param type
     * @return
     */
    public Boolean checkUserData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(user) == 0;
    }

    /**
     * 发送手机验证码
     * @param phone
     */
    public void sendVerifyCode(String phone) {
        // 1. 生成6位验证码
        String code = NumberUtils.generateCode(6);

        // 2. 封装短信消息
        HashMap<String, String> msg = new HashMap<>(2);
        msg.put("phone", phone);
        msg.put("code", code);

        // 3. 发送验证码消息(rabbitMQ)
        amqpTemplate.convertAndSend("picochain.sms.exchange", "sms.verify.code", msg);

        // 4. 保存验证码
        redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
    }

    /**
     * 用户注册
     * @param user
     * @param code
     */
    public void register(User user, String code) {
        // 注：用户名和密码的格式校验使用Hibernate-validator框架的注解来完成
        // 校验短信验证码
        String cacheCode = redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!StringUtils.equals(cacheCode, code)) {
            return;
        }
        log.info("验证码校对成功");
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        log.info("生成盐成功");
        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        // 填充其他字段
        user.setId(null);
        user.setCreated(new Date());

        // 添加进数据库
        int count = userMapper.insertSelective(user);
        if (count != 1) {
            throw new PcException(ExceptionEnum.USER_SAVE_ERROR);
        }
        log.info("用户保存成功");
        // 注册成功，删除redis中验证码的记录
        redisTemplate.delete(KEY_PREFIX + user.getPhone());
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        // 查询
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);

        // 校验用户名和密码
        if (user == null || !user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            return null;
        }

        return user;
    }
}
