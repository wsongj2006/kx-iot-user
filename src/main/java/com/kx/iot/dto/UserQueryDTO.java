package com.kx.iot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserQueryDTO {
    @ApiModelProperty(value = "用户ID，多个id之间用逗号隔开")
    private String ids;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    @ApiModelProperty(value = "职位")
    private String title;

    @ApiModelProperty(value = "所属公司ID")
    private String customerId;

    @ApiModelProperty(value = "所属子公司ID")
    private String subCompanyId;

    @ApiModelProperty(value = "所属部门ID")
    private String sectionId;

    @ApiModelProperty(value = "性别，0=女，1=男")
    private Integer gender;

    @ApiModelProperty(value = "页码")
    private int pageNum;

    @ApiModelProperty(value = "每页显示数量")
    private int counts;

    public List<Long> getIdList() {
        if (StringUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return Arrays.asList(ids.trim().split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
    }
}
