package com.picochain.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author mafeng
 * @date 2020/6/27 20:49
 */

@Data
@Table(name = "pc_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 6, max = 32, message = "用户名只能在6~32位之间")
    private String username;

    @JsonIgnore
    @Length(min = 6, max = 16, message = "密码只能在6~32位之间")
    private String password;

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private String city;
    private Date created;

    @JsonIgnore
    private String salt;
}
