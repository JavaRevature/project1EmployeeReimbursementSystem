package com.revature.ers_backend.models.DTOs;

import com.revature.ers_backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {
    String jwtToken;
    String username;
    Role role;
}
