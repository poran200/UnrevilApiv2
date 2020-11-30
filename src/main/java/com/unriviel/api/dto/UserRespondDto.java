package com.unriviel.api.dto;


import com.unriviel.api.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespondDto {
    private String userName;
    private String email;
    private String fullName;
    private Set<Role> userRoles;
    private boolean isAccountNotLocked;
    private  String profilelink;
}
