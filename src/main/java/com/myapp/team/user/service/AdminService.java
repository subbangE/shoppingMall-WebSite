package com.myapp.team.user.service;

import com.myapp.team.Board.Question.Question;
import com.myapp.team.product.Product;
import com.myapp.team.user.mapper.AdminMapper;
import com.myapp.team.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public void updateUserRole(String userId, String role) {
        adminMapper.updateUserRole(userId, role);
    }
    public List<User> findAllUser() {
        return adminMapper.findAllUser();
    }

    public List<Product> findAllProduct() {
        return adminMapper.findAllProd();
    }

    public List<Question> findAllQuestion() {
        return adminMapper.findAllQuestion();
    }

}
