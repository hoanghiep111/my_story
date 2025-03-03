package com.shop.my.service;

import com.shop.my.dto.request.UserCreateRequest;
import com.shop.my.dto.request.UserUpdateRequest;
import com.shop.my.entity.User;
import com.shop.my.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    // gọi xuống cái repository
    @Autowired
    private  UserRepository userRepository;

    public User createUser(UserCreateRequest request){ // request những thông tin ần thiết tạo ra table user
        // tạo usser mơi

        // khai báo user
        User user = new User();

        // map dữ liệu vào user
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        // gọi tới UserRepository
        return userRepository.save(user);

    }

    public User updateUser(Long userId, UserUpdateRequest request){
        User user = getUser(userId);
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    public void deleteUserId(Long userId){
        userRepository.deleteById(userId);
    }

    public List<User> getUsers(){
        return userRepository.findAll();  // get tất cả user
    }

    public User getUser(Long id){
        return  userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found") );
    }



}
