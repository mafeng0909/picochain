package com.picochain.auth.client;


import com.picochain.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author mafeng
 * @date 2020/6/2 22:05
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
