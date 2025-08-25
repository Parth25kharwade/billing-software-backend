package com.parth.billingsoftware.io;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class AuthRequest {
    private String email;
    private String password;
}
