package com.example.demo.service;

import com.example.demo.bo.CreateUserRequest;
import com.example.demo.bo.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Status;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName((request.getName()));
        userEntity.setStatus(Status.ACTIVE);
        userEntity = userRepository.save(userEntity);
        UserResponse response = new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getStatus().toString());
        return response;
    }

    @Override
    public UserResponse updateUser(Long id, Status status) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID provided.");
        }

        UserEntity userToUpdate = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userToUpdate.setStatus(status);
        userToUpdate = userRepository.save(userToUpdate);
        return new UserResponse(userToUpdate.getId(), userToUpdate.getName(), userToUpdate.getStatus().toString());
    }

    @Override
    public List<UserEntity> getUserByStatus(Status status) {
        return userRepository.findAll().stream().filter(user -> status.equals(user.getStatus())).collect(Collectors.toList());
    }

}
