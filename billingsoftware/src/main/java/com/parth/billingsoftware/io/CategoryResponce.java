package com.parth.billingsoftware.io;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
@Builder
@Data
public class CategoryResponce {
    private String categoryId;
    private String name;
    private String description;
    private String bgColor;
    private String imgUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer items;
}
