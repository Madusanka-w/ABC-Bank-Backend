package com.authentication.jwt.models.dto;

import com.authentication.jwt.models.entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserCreationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String nic;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String password;


}
