package com.authentication.jwt.auth;

import com.authentication.jwt.models.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String nic;
    private String contactNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private Role role;

}
