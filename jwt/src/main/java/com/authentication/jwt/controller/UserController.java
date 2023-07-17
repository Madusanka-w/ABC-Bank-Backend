package com.authentication.jwt.controller;


import com.authentication.jwt.models.ResponseWrapper;
import com.authentication.jwt.models.entities.Role;
import com.authentication.jwt.models.entities.User;
import com.authentication.jwt.models.dto.UserCreationDto;
import com.authentication.jwt.repository.RoleRepository;
import com.authentication.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;



    @PostMapping("/dto")
    public ResponseWrapper createFromDto (@RequestBody UserCreationDto userCreationDto) {
        try{
            User user = userService.createFromDto(userCreationDto);
            return new ResponseWrapper<>(user, "success", "created");
        }catch (Exception e){
            return new ResponseWrapper<>(null, "failed", e.getMessage());
        }
    }

    @GetMapping
    public ResponseWrapper getAllUsers (){

        try{
            return new ResponseWrapper<>(userService.getAllUsers(), "success", "fetched");
        }catch (Exception e){
            return new ResponseWrapper<>(null, "failed", e.getMessage());
        }

    }

    @PatchMapping("/updatePassword/{email}/{password}")
    public User updatePassword(@PathVariable String email, @PathVariable String password){
        try {
            return userService.updatePassword(email, password);
        }catch (Exception e){
            return new User();
        }
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return userService.updateUser(id, user);
        }catch (Exception e){
            return new User();
        }
    }

//    @PatchMapping("/userupdate/{id}")
//    public User userUpdate(@PathVariable Long)

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            return userService.deleteUserById(id);
        }catch (Exception e){
            return e.getMessage();
        }
    }




}
