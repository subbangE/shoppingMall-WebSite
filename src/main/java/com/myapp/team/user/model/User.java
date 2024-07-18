package com.myapp.team.user.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private int userNo;

    private String userId;

    private String userPw;

    private String userName;

    private String userBirth;

    private String userAddress;

    private String userEmail;

    private String userRank;

    private String role;

}
