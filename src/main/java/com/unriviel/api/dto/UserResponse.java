package com.unriviel.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userName;
    private String email;
    private String fullName;
    private List<String> roles;
    private boolean isAccountNoneLocked;
    private boolean isEnable;
    private  String profileLink;

    @Override
    public String toString() {
        return "UserResponse{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roles=" + roles.toString() +
                ", isAccountNotLocked=" + isAccountNoneLocked +
                ", isActive=" + isEnable +
                ", profilelink='" + profileLink + '\'' +
                '}';
    }
}