package com.example.demo.service;

import com.example.demo.bo.CreateUserRequest;
import com.example.demo.bo.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Status;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, Status status);
    List<UserEntity> getUserByStatus(Status status);
}
