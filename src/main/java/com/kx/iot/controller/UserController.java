package com.kx.iot.controller;

import com.kx.iot.dto.UserDTO;
import com.kx.iot.dto.UserQueryDTO;
import com.kx.iot.model.PageWrapper;
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


    @PostMapping
    @ApiOperation("新建用户")
    public HttpResponse<Long> createUser(
            @RequestHeader(required = false) String token,
            @RequestBody UserDTO userDTO
    ) {
        Long userId = userService.createUser(userDTO);
        return createResponse(userId);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{userId}")
    public HttpResponse delete(
            @RequestHeader(required = false) String token,
            @PathVariable Long userId
    ) {
        userService.delete(userId);
        return createResponse(null);
    }

    @GetMapping
    @ApiOperation("查询用户")
    public HttpListResponse<UserDTO> find(
            @RequestHeader(required = false) String token,
            @ModelAttribute UserQueryDTO userQueryDTO
    ) {
        PageWrapper<UserDTO> userDTOPageWrapper = userService.find(userQueryDTO);
        HttpListResponse<UserDTO> httpListResponse = new HttpListResponse<UserDTO>();
        httpListResponse.setData(userDTOPageWrapper.getDataList());
        httpListResponse.buildStatus(UserResponseCode.SUCCESS.getCode(), UserResponseCode.SUCCESS.getMessage())
                .buildPaging(userDTOPageWrapper.getTotal(), userDTOPageWrapper.getPages(), userDTOPageWrapper.getPageNo(), userDTOPageWrapper.getPageSize());
        return httpListResponse;
    }

    @GetMapping("/{userId}")
    @ApiOperation("查询单个用户")
    public HttpResponse<UserDTO> findById(
            @RequestHeader(required = false) String token,
            @PathVariable Long userId
    ) {
        UserDTO userDTO = userService.findById(userId);
        return createResponse(userDTO);
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
