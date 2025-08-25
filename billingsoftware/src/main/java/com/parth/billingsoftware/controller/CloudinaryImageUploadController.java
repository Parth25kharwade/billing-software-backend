package com.parth.billingsoftware.controller;

import com.parth.billingsoftware.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin(origins = "*")
public class CloudinaryImageUploadController {
    @Autowired
    private CloudinaryImageService cloudinaryImageService;
    @PostMapping("upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("image")MultipartFile file){
        Map data=this.cloudinaryImageService.upload(file);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@RequestParam("url") String url) {
        boolean deleted = cloudinaryImageService.delet(url);
        if (deleted) {
            return ResponseEntity.ok("Image deleted successfully.");
        } else {
            return ResponseEntity.status(500).body("Image deletion failed.");
        }
    }
}
