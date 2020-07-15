package com.kx.iot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kx.iot.response.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户中心")
public class UserController {

    @GetMapping
    @ApiOperation("获取用户名")
    public String getUserName() {
        HttpResponse httpResponse = new HttpResponse();
        return "Andy";
    }
}
