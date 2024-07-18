package com.myapp.team.user.service;

import com.myapp.team.user.config.CustomUserDetails;
import com.myapp.team.user.mapper.UserMapper;
import com.myapp.team.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.findByUserId(userId);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException(userId + "에 해당하는 사용자를 찾을 수 없습니다.");
        }

//
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = user.getRole();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role));
        } else {
            // Handle the case where role is null, e.g., log an error or throw an exception
            throw new IllegalArgumentException("User role cannot be null");
        }

        return new com.myapp.team.user.config.CustomUserDetails(user, authorities);
    }

}
