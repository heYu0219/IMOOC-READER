package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoheyu.reader.entity.User;
import com.xiaoheyu.reader.mapper.UserMapper;
import com.xiaoheyu.reader.service.UserService;
import com.xiaoheyu.reader.service.exception.BussinessException;
import com.xiaoheyu.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User checkLogin(String username, String password) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            throw new BussinessException("L001","用户名不存在");
        }
        String md5= MD5Utils.MD5Digest(password, user.getSalt());
        if(!md5.equals(user.getPassword())){
            throw new BussinessException("L002","密码错误");
        }
        return user;
    }
}
