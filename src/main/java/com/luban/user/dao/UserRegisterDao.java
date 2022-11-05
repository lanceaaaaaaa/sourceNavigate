package com.luban.user.dao;

import com.luban.user.model.UserRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserRegisterDao {
    UserRegister selectUserByName(String name);

    void updateLockedByUserId(String name);
}
