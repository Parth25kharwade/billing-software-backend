package com.parth.billingsoftware.controller;



import com.parth.billingsoftware.dto.UserRequest;

import com.parth.billingsoftware.dto.UserResponse;
import com.parth.billingsoftware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest request){
        try {
            return userService.createUser(request);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/admin/users")
    public List<UserResponse> readUsers() {

        return userService.readUsers();
    }

    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        try {
            userService.deleteUser(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
