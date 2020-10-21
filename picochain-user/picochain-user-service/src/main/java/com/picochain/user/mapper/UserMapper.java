package com.picochain.user.mapper;

import com.picochain.user.pojo.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author mafeng
 * @date 2020/6/27 21:18
 */
@Component
public interface UserMapper extends Mapper<User> {
}
