package com.parth.billingsoftware.service;

import com.parth.billingsoftware.io.CategoryRequest;
import com.parth.billingsoftware.io.CategoryResponce;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponce add(CategoryRequest request, MultipartFile file);
    List<CategoryResponce> read();
    void delete(String categoryId);
}
