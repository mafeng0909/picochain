package com.picochain.user.controller;

import com.picochain.user.pojo.User;
import com.picochain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mafeng
 * @date 2020/6/27 20:48
 */
@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 检验数据是否可用，即手机号和用户名是否唯一
     *
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkUserData(data, type));
    }

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("sendCode")
    public ResponseEntity<Void> sendVerifyCode(String phone) {
        userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        userService.register(user, code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.queryUser(username, password));
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @PostMapping("queryUser")
    public ResponseEntity<User> queryUser(String username) {
        return ResponseEntity.ok(userService.queryUser(username));
    }

    /**
     *
     * @param username
     * @param oldPsw
     * @param newPsw
     * @return
     */
    @PostMapping("changePsw")
    public ResponseEntity<Boolean> changePsw(@RequestParam("username") String username,
                                             @RequestParam("oldPsw") String oldPsw,
                                             @RequestParam("newPsw") String newPsw) {
        return ResponseEntity.ok(userService.changePsw(username, oldPsw, newPsw));
    }

    /**
     *
     * @param username
     * @param info
     * @param data
     * @return
     */
    @PostMapping("changeInfo")
    public ResponseEntity<Boolean> changeInfo(@RequestParam("username") String username,
                                              @RequestParam("info") String info,
                                              @RequestParam("data") String data) {
        return ResponseEntity.ok(userService.changeInfo(username, info, data));
    }
}
