package com.example.demo.controller;

import com.example.demo.bo.CreateUserRequest;
import com.example.demo.bo.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Status;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createuser(@RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);

        // Check if the response is not null (indicating a successful creation)
        if (response != null) {
            // Return a 201 Created status code along with the created user data
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            // Handle the case where the creation was not successful (e.g., validation failed)
            // You can return a different status code or error message as needed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<UserResponse> updateuser(@RequestParam Long id, @RequestParam Status status) {
        UserResponse response = userService.updateUser(id, status);

        // Check if the response is not null (indicating a successful creation)
        if (response != null) {
            // Return a 201 Created status code along with the created user data
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            // Handle the case where the creation was not successful (e.g., validation failed)
            // You can return a different status code or error message as needed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/searchUsers")
    public ResponseEntity<List<UserEntity>> getUserByStatus(@RequestParam String status) {
        try {
            Status validStatus = Status.valueOf(status.toUpperCase());
            List<UserEntity> users = userService.getUserByStatus(validStatus);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
    }

}
