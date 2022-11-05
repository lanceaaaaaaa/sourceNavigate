package com.luban.user.service.impl;

import com.luban.user.dao.UserRegisterDao;
import com.luban.user.model.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRegisterDao userRegisterDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegister user = userRegisterDao.selectUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setAuthorities(authorities);
        return user;
    }

    public void updateLockedByUserId(String userName){
        userRegisterDao.updateLockedByUserId(userName);
    }
}
