package com.picochain.auth.service;

import com.picochain.auth.client.UserClient;
import com.picochain.auth.config.JwtProperties;
import com.picochain.auth.entity.UserInfo;
import com.picochain.auth.utils.JwtUtils;
import com.picochain.common.enums.ExceptionEnum;
import com.picochain.common.exception.PcException;
import com.picochain.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author mafeng
 * @date 2020/6/2 21:43
 */
@Service
public class AuthService {

    private final JwtProperties prop;
    private final UserClient userClient;

    @Autowired
    public AuthService(JwtProperties prop, UserClient userClient) {
        this.prop = prop;
        this.userClient = userClient;
    }

    /**
     * 登录授权
     * @param username
     * @param password
     */
    public String authentication(String username, String password) {
        // 调用微服务，执行查询
        User user = userClient.queryUser(username, password);
        if (user == null) {
            throw new PcException(ExceptionEnum.TOKEN_GENERATE_FAILED);
        }

        try {
            // 生成token
            return JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    prop.getPrivateKey(), prop.getExpire());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

