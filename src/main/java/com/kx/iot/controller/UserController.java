package com.kx.iot.controller;

import com.kx.iot.dto.UserDTO;
import com.kx.iot.service.UserService;
import com.kx.iot.util.TokenThreadLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kx.iot.response.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户中心")
public class UserController {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation("获取用户名")
    public String getUserName() {
        HttpResponse httpResponse = new HttpResponse();
        return "Andy";
    }

    @PostMapping
    @ApiOperation("新建用户")
    public HttpResponse<Long> createUser(
            @RequestHeader(required = false) String token,
            @RequestBody UserDTO userDTO
    ) {
        setToken(token);
        Long userId = userService.createUser(userDTO);
        return createResponse(userId);
    }

    private HttpResponse createResponse(Object data) {
        HttpResponse response = new HttpResponse();
        response.setData(data);
        response.buildStatus(UserResponseCode.SUCCESS.getCode(), UserResponseCode.SUCCESS.getMessage());
        return response;
    }

    private void setToken(String token) {
        TokenThreadLocal.setToken(token);
    }
}
