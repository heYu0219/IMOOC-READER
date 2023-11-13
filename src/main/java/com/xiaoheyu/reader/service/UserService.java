package com.xiaoheyu.reader.service;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.xiaoheyu.reader.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User checkLogin(String username, String password);
}
