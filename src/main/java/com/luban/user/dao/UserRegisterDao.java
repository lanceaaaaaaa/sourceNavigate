package com.luban.user.dao;

import com.luban.user.model.UserRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRegisterDao {
    UserRegister selectUserByName(@Param("username") String name);
}
