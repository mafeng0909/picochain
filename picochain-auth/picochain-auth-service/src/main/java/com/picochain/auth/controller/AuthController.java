package com.picochain.auth.controller;

import com.picochain.auth.config.JwtProperties;
import com.picochain.auth.entity.UserInfo;
import com.picochain.auth.service.AuthService;
import com.picochain.auth.utils.JwtUtils;
import com.picochain.common.enums.ExceptionEnum;
import com.picochain.common.exception.PcException;
import com.picochain.common.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mafeng
 * @date 2020/6/2 21:42
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    private final AuthService authService;
    private final JwtProperties prop;

    @Autowired
    public AuthController(AuthService authService, JwtProperties prop) {
        this.authService = authService;
        this.prop = prop;
    }

    /**
     * 登录授权
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> authentication(
                @RequestParam("username") String username,
                @RequestParam("password") String password,
                HttpServletRequest request,
                HttpServletResponse response) {
        String token = authService.authentication(username, password);
        // 校验token
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 将token写入cookie, 并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getCookieMaxAge(), true);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 验证用户信息
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("PC_TOKEN")String token,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        try {
            // 从token中解析用户信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            // 解析成功要重新刷新token
            String newToken = JwtUtils.generateToken(userInfo, prop.getPrivateKey(), prop.getExpire());
            // 更新cookie中的token
            CookieUtils.setCookie(request, response, prop.getCookieName(), newToken, prop.getCookieMaxAge());
            // 解析成功，返回用户信息
            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            throw new PcException(ExceptionEnum.UN_AUTHORIZED);
        }
    }
}
