package com.myapp.team.user.controller;

import com.myapp.team.Board.Question.Question;
import com.myapp.team.user.model.User;
import com.myapp.team.user.service.AdminService;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@PreAuthorize("#userNo == authentication.principalgit.userNo")



public class mypageController {

    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @GetMapping("/user/{userNo}")
    @PreAuthorize("isAuthenticated()")
    public String mypage(@PathVariable("userNo") int userNo, Model model) {
        User user = userService.getUserByUserNo(userNo);
        model.addAttribute("user", user);
        return "mypage";
    }

    @GetMapping("/qlist/{userNo}")
    @PreAuthorize("isAuthenticated()")
    public String qlistPage(@PathVariable("userNo") int userNo, Model model) {
        User user = userService.getUserByUserNo(userNo);
        List<Question> questionList = adminService.findAllQuestion();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        model.addAttribute("userName",userName);
        model.addAttribute("questionList", questionList);
        model.addAttribute("user", user);

        return "mypageQuestion";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user, Model model) {
        System.out.println("유저번호: "+ user.getUserNo());
        userService.updateUser(user);
        User updateUser = userService.getUserByUserNo(user.getUserNo());
        model.addAttribute("user", updateUser);
        return "redirect:/mypage/user/" + user.getUserNo();
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userId) {
        System.out.println(userId);
        userService.deleteUserByUserNo(userId);
        return "deleteSuccess";
    }

}
