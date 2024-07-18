package com.myapp.team.user.mapper;

import com.myapp.team.Board.Question.Question;
import com.myapp.team.product.Product;
import com.myapp.team.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    void updateUserRole(@Param("userId") String userId, @Param("role") String role);

    List<User> findAllUser();

    List<Product> findAllProd();

    List<Question> findAllQuestion();

}
