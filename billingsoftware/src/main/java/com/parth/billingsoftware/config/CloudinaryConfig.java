package com.parth.billingsoftware.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary(){
        Map map =new HashMap();
        map.put("cloud_name","deuq2mi0r");
        map.put("api_key","193541318544354");
        map.put("api_secret","e9bCxbu5xgO5ovUTXdniK52P4QA");
        map.put("secure",true);
     return new Cloudinary(map);
   }
}
