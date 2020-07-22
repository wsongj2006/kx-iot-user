package com.kx.iot.dao;

import tk.mybatis.mapper.common.BaseMapper;
import com.kx.iot.entity.UserEntity;
import tk.mybatis.mapper.common.ConditionMapper;

public interface UserMapper extends ConditionMapper<UserEntity>, BaseMapper<UserEntity> {
}
