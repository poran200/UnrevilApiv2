package com.unriviel.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data@NoArgsConstructor
public class UserResponseDto  implements Serializable  {
    private String userName;
    private String email;
    private String fullName;
}
