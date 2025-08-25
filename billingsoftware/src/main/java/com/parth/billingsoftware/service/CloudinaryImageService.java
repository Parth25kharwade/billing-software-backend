package com.parth.billingsoftware.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryImageService {
    public Map upload(MultipartFile file);
    public boolean delet(String url);
}
