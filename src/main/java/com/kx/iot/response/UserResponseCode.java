package com.kx.iot.response;

public enum UserResponseCode {
    SUCCESS(200, "Success"),
    USER_NAME_REQUIRED(10020, "User Name is required!"),
    COMPANY_REQUIRED(10021, "公司名称不能为空."),
    PASSWORD_REQUIRED(10022, "密码不能为空."),
    USER_TYPE_REQUIRED(10023, "用户类型不能为空"),
    CANNOT_CREATE_USER(10024, "您不能创建经销商账户"),
    USER_NAME_EXISTING(10025, "用户名已经存在");

    private Integer code;
    private String message;

    UserResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
