package com.parth.billingsoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parth.billingsoftware.io.CategoryRequest;
import com.parth.billingsoftware.io.CategoryResponce;
import com.parth.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponce addCategories(@RequestPart("category") String categoryString,
                                          @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper= new ObjectMapper();
        CategoryRequest request=null;
        try{
            request=objectMapper.readValue(categoryString,CategoryRequest.class);
            return categoryService.add(request,file);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"EXCEPTION OCCURED WHILE PARSING JSON: "+e);
        }

    }
    @GetMapping("/categories")
    public List<CategoryResponce> fetchCategories(){
        return categoryService.read();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/categories/{categoryId}")
    public void remove(@PathVariable String categoryId){
        try {
            categoryService.delete(categoryId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
