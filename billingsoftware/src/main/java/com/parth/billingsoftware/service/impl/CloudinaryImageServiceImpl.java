package com.parth.billingsoftware.service.impl;

import com.cloudinary.Cloudinary;
import com.parth.billingsoftware.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public Map upload(MultipartFile file) {
        try {
             Map data=this.cloudinary.uploader().upload(file.getBytes(),Map.of());
             return data;
        } catch (IOException e) {
            throw new RuntimeException("Image Uploding Fail !!");
        }
    }

    @Override
    public boolean delet(String url) {
        try {

            String[] parts = url.split("/");
            String publicIdWithExtension = parts[parts.length - 1];
            String publicId = publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf('.'));


            Map result = cloudinary.uploader().destroy(publicId, Map.of());


            return "ok".equals(result.get("result"));
        } catch (Exception e) {
            throw new RuntimeException("Image deletion failed !!");
        }
    }

}
