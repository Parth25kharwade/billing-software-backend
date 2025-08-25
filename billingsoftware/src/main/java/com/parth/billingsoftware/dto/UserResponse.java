package com.parth.billingsoftware.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserResponse {
    private String userId;
    private String email;
    private String role;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}