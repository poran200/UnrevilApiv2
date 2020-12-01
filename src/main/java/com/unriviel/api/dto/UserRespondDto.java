package com.unriviel.api.dto;


import com.unriviel.api.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespondDto {
    private String userName;
    private String email;
    private String fullName;
    private List<String> roles;
    private boolean isAccountNotLocked;
    private boolean isActive;
    private  String profilelink;

    @Override
    public String toString() {
        return "UserRespondDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roles=" + roles.toString() +
                ", isAccountNotLocked=" + isAccountNotLocked +
                ", isActive=" + isActive +
                ", profilelink='" + profilelink + '\'' +
                '}';
    }
}
