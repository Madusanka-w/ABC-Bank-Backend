package com.authentication.jwt.controller;


import com.authentication.jwt.models.ResponseWrapper;
import com.authentication.jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    RoleService roleService;


    @GetMapping
    public ResponseWrapper getAllRoles (){

        try{
            return new ResponseWrapper<>(roleService.findAll(), "success", "fetched");
        }catch (Exception e){
            return new ResponseWrapper<>(null, "failed", e.getMessage());
        }

    }

    @PostMapping("/byName")
    public ResponseWrapper createRoleByName (@RequestBody String roleName){

        try{
            return new ResponseWrapper<>(roleService.create(roleName), "success", "created");
        }catch (Exception e){
            return new ResponseWrapper<>(null, "failed", e.getMessage());
        }

    }
}
