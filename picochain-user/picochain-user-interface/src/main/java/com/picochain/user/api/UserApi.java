package com.picochain.user.api;

import com.picochain.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mafeng
 * @date 2020/7/2 20:51
 */

public interface UserApi {

    /**
     * 根据用户名和密码来查询指定用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    User queryUser(@RequestParam("username")String username,
                   @RequestParam("password")String password);
}
