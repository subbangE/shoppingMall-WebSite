package com.myapp.team.user.controller;

import com.myapp.team.Board.Question.Question;
import com.myapp.team.product.Product;
import com.myapp.team.user.model.User;
import com.myapp.team.user.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/grant")
    public String grantAdmin(@RequestParam String userId, @RequestParam String role, Model model, HttpServletRequest request) {
        if(!"USER".equals(role)) {
            adminService.updateUserRole(userId, "ADMIN");
        }else {
            adminService.updateUserRole(userId, "USER");
        }

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    //ADMIN 역할을 가진 유저만 페이지에 입장가능하게 해줌
    @GetMapping

    public String adminPage() {
        return "admin";
    }

    @GetMapping("/userlist")
    public String userListPage(Model model) {
        List<User> userList = adminService.findAllUser();
        List<User> userRoleUsers = userList.stream()
                .filter(user -> "USER".equals(user.getRole()))
                .collect(Collectors.toList());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        model.addAttribute("users", userRoleUsers);
        model.addAttribute("userRoleUserCount", userRoleUsers.size());
        model.addAttribute("userName", userName);
        return "userlist";
    }

    @GetMapping("/adminlist")
    public String adminListPage(Model model) {
        List<User> userList = adminService.findAllUser();
        List<User> userRoleAdmin = userList.stream()
                .filter(user -> "ADMIN".equals(user.getRole()))
                .collect(Collectors.toList());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        model.addAttribute("users", userList);
        model.addAttribute("userRoleAdminCount", userRoleAdmin.size());
        model.addAttribute("userName", userName);
        return "adminlist";
    }

    @GetMapping("/prodlist")
    public String prodListPage(Model model) {
        List<Product> prodList = adminService.findAllProduct();
        List<Product> prodCount = prodList.stream()
                .filter(product -> !"".equals(product.getProductNo()))
                .collect(Collectors.toList());
        long productNoCount = prodCount.size();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        model.addAttribute("prods", prodList);
        model.addAttribute("userName", userName);
        model.addAttribute("productNoCount",productNoCount);

        return "prodlist";
    }

    @GetMapping("/questionlist")
    public String QuestionListPage(Model model) {
        List<Question> questionList = adminService.findAllQuestion();
        List<Question> questionCount = questionList.stream()
                .filter(Question -> !"".equals(Question.getQuestionNo()))
                .collect(Collectors.toList());
        long questionNoCount = questionCount.size();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        model.addAttribute("questions", questionList);
        model.addAttribute("userName", userName);
        model.addAttribute("questionNoCount",questionNoCount);
        return "questionlist";
    }
}
