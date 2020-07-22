package com.kx.iot.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "user_info")
@Data
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String avatar;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "real_name")
    private String realName;

    private String phone;

    @Column(name = "depart_id")
    private Long departId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "sub_company_id")
    private Long subCompanyId;

    @Column(name = "section_id")
    private Long sectionId;

    private String title;

    private Integer age;

    private Integer gender;

    private String email;

    private Integer status;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "line_id")
    private Long lineId;

    private Integer type;
}
