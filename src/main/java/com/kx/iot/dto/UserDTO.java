package com.kx.iot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ApiModel("用户实体")
public class UserDTO {
    private Long id;

    private String idStr;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "身份证号码")
    private String idNumber;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "所属公司id")
    private String customerId;

    @ApiModelProperty(value = "所属子公司id")
    private String subCompanyId;

    @ApiModelProperty(value = "所属部门id")
    private String sectionId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司营业执照号码")
    private String licenseNumber;

    @ApiModelProperty(value = "部门ID")
    private String departId;

    @ApiModelProperty(value = "职位")
    private String title;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别，0=女，1=男")
    private Integer gender;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户类别， 1=经销商用户，0=终端用户")
    private Integer type;
}
