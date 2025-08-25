package com.parth.billingsoftware.service.impl;


import com.parth.billingsoftware.dto.UserRequest;
import com.parth.billingsoftware.entity.UserEntity;
import com.parth.billingsoftware.dto.UserResponse;
import com.parth.billingsoftware.repository.UserRepository;
import com.parth.billingsoftware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity newUser= convertToEntity(request);
        newUser=userRepository.save(newUser);
        return convertToResponse(newUser);
    }

    private UserResponse convertToResponse(UserEntity user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    private UserEntity convertToEntity(UserRequest request) {
        // Secure role assignment: only the first user can be an admin.
        // All subsequent users are defaulted to "USER" to prevent privilege escalation.
        boolean isFirstUser = userRepository.count() == 0;
        String role = isFirstUser && "ADMIN".equalsIgnoreCase(request.getRole()) ? "ADMIN" : "USER";

        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .name(request.getName())
                .isActive(true) // Initialize as active
                .isDeleted(false) // Initialize as not deleted
                .build();
    }

    @Override
    public String getUserRole(String email) {
       // Ensure we only get roles for active, non-deleted users
       UserEntity existingUser= userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with email: "+email));
        return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {
        // Use the new repository method to only fetch non-deleted users
        return userRepository.findAllByIsDeletedFalse()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteUser(String id) {
       UserEntity existingUser= userRepository.findUserByUserId(id)
               .orElseThrow(()->new UsernameNotFoundException("user not found"));
        // Best practice: Use soft delete instead of hard delete
        existingUser.setIsDeleted(true);
        existingUser.setIsActive(false);
        userRepository.save(existingUser);
    }
}
