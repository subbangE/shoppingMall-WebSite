package com.myapp.team.user.mapper;

import com.myapp.team.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {

    void insertUser(User user);

    User findByUserId(String userId);

    String findByUserNo(int userNo);

    void updateUser(User user);

    User getUserByUserNo(int UserNo);

    User getUserByUsername(String UserName);

    void deleteUserByUserId(String UserId);

    User getUserNo(int userNO);


    User findByUserName(String userName);
}
