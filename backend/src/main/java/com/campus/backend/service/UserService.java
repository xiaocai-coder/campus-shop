package com.campus.backend.service;

import com.campus.backend.dto.LoginRequest;
import com.campus.backend.dto.LoginResponse;
import com.campus.backend.entity.User;
import com.campus.backend.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getUsername());

        if (user == null) {
            throw new RuntimeException("账号不存在");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setRole(user.getRole());
        response.setToken("mock-token-" + user.getRole());

        return response;
    }
}