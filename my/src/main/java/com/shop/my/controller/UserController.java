package com.shop.my.controller;

import com.shop.my.dto.request.user.UserCreateRequest;
import com.shop.my.dto.request.user.UserUpdateRequest;
import com.shop.my.entity.User;
import com.shop.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // đặt tên api
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody UserCreateRequest request) { // map data của body vào object (  UserCreatRequest )
       return userService.createUser(request);
    }

    @GetMapping
    List<User> getUsers(){
       return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") Long userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return  userService.updateUser(userId,request);

    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable Long userId){
        userService.deleteUserId(userId);
        return  "User has been delete";
    }



}
