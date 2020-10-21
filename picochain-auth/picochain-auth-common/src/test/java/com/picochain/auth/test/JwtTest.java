package com.picochain.auth.test;

import com.picochain.auth.entity.UserInfo;
import com.picochain.auth.utils.JwtUtils;
import com.picochain.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author mafeng
 * @date 2020/7/4 22:19
 */
public class JwtTest {

    private static final String pubKeyPath = "F:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "F:\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5Mzg3MzA5MH0.fzEXYK1pisscvu38GyTwKWcsNbb17nVcgf8V2_K4EWpqHmk6A1as_4XIlDivfjsqjdf_fZTfyMfy-wDm5-NFWlDwp6CoKQ7k-2FaBf7oLi-4G2cTO75ufYRT1sECvyNuFw9K00Kaqe035nUwyRneULfDcikMgj907t8RdEjHGCo";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
