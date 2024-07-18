package com.myapp.team.user.service;

import com.myapp.team.user.mapper.UserMapper;
import com.myapp.team.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void register(User user) {
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        user.setRole("USER");
        userMapper.insertUser(user);
    }

    public void updateUser(User user) {
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        userMapper.updateUser(user);
    }

    public User getUserByUserNo(int userNo) {
        return userMapper.getUserByUserNo(userNo);
    }

    public void deleteUserByUserNo(String userId) {
        userMapper.deleteUserByUserId(userId);
    }

    public User getUserNo(int userNo) {
        return userMapper.getUserNo(userNo);
    }

    public User findByUserName(String userName){
        return userMapper.findByUserName(userName);
    }


}
