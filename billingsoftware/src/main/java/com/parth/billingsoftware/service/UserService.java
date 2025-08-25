package com.parth.billingsoftware.service;

import com.parth.billingsoftware.dto.UserRequest;
import com.parth.billingsoftware.dto.UserResponse;


import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    String getUserRole(String email);
    List<UserResponse> readUsers();
    void deleteUser(String id);
}
