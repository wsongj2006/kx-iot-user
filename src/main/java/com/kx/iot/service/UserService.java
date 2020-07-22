package com.kx.iot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kx.iot.dto.UserDTO;
import com.kx.iot.dto.UserQueryDTO;
import com.kx.iot.entity.UserEntity;
import com.kx.iot.exception.ValidationException;
import com.kx.iot.model.PageWrapper;
import com.kx.iot.response.UserResponseCode;
import com.kx.iot.util.JwtTokenUtils;
import com.kx.iot.util.TokenThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.iot.dao.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public Long createUser(UserDTO userDTO) {
        validate(userDTO);
        UserEntity userEntity = convert(userDTO);
        userMapper.insert(userEntity);
        updateUserLine(userEntity);
        return userEntity.getId();
    }

    public int delete(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public PageWrapper<UserDTO> find(UserQueryDTO userQueryDTO) {
        PageHelper.startPage(userQueryDTO.getPageNum(), userQueryDTO.getCounts());

        Condition condition = createQueryCondition(userQueryDTO);
        List<UserEntity> userEntityList = userMapper.selectByCondition(condition);

        PageInfo<UserEntity> pageInfo = new PageInfo<>((userEntityList));

        List<UserDTO> userDTOList = convertFromEntitys(userEntityList);
        return buildPageWrapper(userDTOList, pageInfo);
    }


    private void validate(UserDTO userDTO) {
        if (StringUtils.isEmpty(userDTO.getUserName())) {
            throwException(UserResponseCode.USER_NAME_REQUIRED);
        }
        if (StringUtils.isEmpty(userDTO.getCustomerId())) {
            throwException(UserResponseCode.COMPANY_REQUIRED);
        }
        if (StringUtils.isEmpty(userDTO.getPassword())) {
            throwException(UserResponseCode.PASSWORD_REQUIRED);
        }
        if (userDTO.getType() == null) {
            throwException(UserResponseCode.USER_TYPE_REQUIRED);
        }
        if (1 == userDTO.getType() && 0 == getCurrentUserType()) {
            throwException(UserResponseCode.CANNOT_CREATE_USER);
        }
        if (isUserNameExisting(userDTO)) {
            throwException(UserResponseCode.USER_NAME_EXISTING);
        }
    }

    private void throwException(UserResponseCode userResponseCode) {
        throw new ValidationException(userResponseCode.getCode(), userResponseCode.getMessage());
    }

    private UserEntity convert(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        userEntity.setCustomerId(Long.parseLong(userDTO.getCustomerId()));
        if (!StringUtils.isEmpty(userDTO.getSubCompanyId())) {
            userEntity.setSubCompanyId(Long.parseLong(userDTO.getSubCompanyId()));
        }
        if (!StringUtils.isEmpty(userDTO.getSectionId())) {
            userEntity.setSectionId(Long.parseLong(userDTO.getSectionId()));
        }
        userEntity.setStatus(0);
        userEntity.setLineId(JwtTokenUtils.getLineId(TokenThreadLocal.getToken()));
        userEntity.setParentId(JwtTokenUtils.getUserId(TokenThreadLocal.getToken()));
        return userEntity;
    }

    private void updateUserLine(UserEntity userEntity) {
        if (JwtTokenUtils.isSupperAdmin(TokenThreadLocal.getToken())) {
            userEntity.setLineId(userEntity.getId());
            userMapper.updateByPrimaryKeySelective(userEntity);
        }
    }

    private Integer getCurrentUserType() {
        return JwtTokenUtils.getType(TokenThreadLocal.getToken());
    }

    private boolean isUserNameExisting(UserDTO userDTO) {
        Condition condition = new Condition(UserEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("userName", userDTO.getUserName());
        int existCounts = userMapper.selectCountByCondition(condition);
        return existCounts > 0;
    }

    private Condition createQueryCondition(UserQueryDTO userQueryDTO) {
        Condition condition = new Condition(UserEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        if (!StringUtils.isEmpty(userQueryDTO.getUserName())) {
            criteria.andEqualTo("userName", userQueryDTO.getUserName());
        }
        if (userQueryDTO.getAge() != null && userQueryDTO.getAge() > 0) {
            criteria.andEqualTo("age", userQueryDTO.getAge());
        }
        if (!StringUtils.isEmpty(userQueryDTO.getCustomerId())) {
            criteria.andEqualTo("customerId", userQueryDTO.getCustomerId());
        }
        if (!StringUtils.isEmpty(userQueryDTO.getSectionId())) {
            criteria.andEqualTo("sectionId", userQueryDTO.getSectionId());
        }
        if (!StringUtils.isEmpty(userQueryDTO.getSubCompanyId())) {
            criteria.andEqualTo("subCompanyId", userQueryDTO.getSubCompanyId());
        }
        if (!StringUtils.isEmpty(userQueryDTO.getTitle())) {
            criteria.andEqualTo("title", userQueryDTO.getTitle());
        }
        if (userQueryDTO.getGender() != null && userQueryDTO.getGender() >= 0) {
            criteria.andEqualTo("gender", userQueryDTO.getGender());
        }
        if (!userQueryDTO.getIdList().isEmpty()) {
            criteria.andIn("id", userQueryDTO.getIdList());
        }
        return condition;
    }

    private List<UserDTO> convertFromEntitys(List<UserEntity> userEntityList) {
        return userEntityList.stream()
                .map(userEntity -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(userEntity, userDTO);
                    userDTO.setIdStr(userEntity.getId().toString());
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    private PageWrapper<UserDTO> buildPageWrapper(List<UserDTO> userDTOList, PageInfo<UserEntity> pageInfo) {
        PageWrapper<UserDTO> pageWrapper = new PageWrapper<>();
        pageWrapper.setDataList(userDTOList);
        pageWrapper.setPages(pageInfo.getPages());
        pageWrapper.setPageSize(pageInfo.getPageSize());
        pageWrapper.setPageNo(pageInfo.getPageNum());
        pageWrapper.setTotal(pageInfo.getTotal());
        return pageWrapper;
    }
}
