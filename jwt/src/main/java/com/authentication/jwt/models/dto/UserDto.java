package com.authentication.jwt.models.dto;

import com.authentication.jwt.models.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String nic;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String country;
    
    private List<Role> roles;
}
