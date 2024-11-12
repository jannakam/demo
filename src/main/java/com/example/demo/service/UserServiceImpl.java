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
public class UserServiceImpl implements UserService{

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

        // validate id
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID provided.");
        }

        // validate status
        if (!(status.equals(Status.ACTIVE) || status.equals(Status.INACTIVE))) {
            throw new IllegalArgumentException("Invalid status provided.");
        }
        UserEntity userToUpdate = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userToUpdate.setStatus(status);
        userToUpdate = userRepository.save(userToUpdate);
        UserResponse response = new UserResponse(userToUpdate.getId(), userToUpdate.getName(), userToUpdate.getStatus().toString());
        return response;
    }

    @Override
    public List<UserEntity> getUserByStatus(Status status) {

        // validate status
        if (!(status.equals(Status.ACTIVE) || status.equals(Status.INACTIVE))) {
            throw new IllegalArgumentException("Invalid status provided.");
        }

        List<UserEntity> usersList = getAllUsers();

        List<UserEntity> filteredUsers = usersList.stream()
                .filter(user -> status.equals(user.getStatus()))
                .collect(Collectors.toList());

        return filteredUsers;
    }
}
