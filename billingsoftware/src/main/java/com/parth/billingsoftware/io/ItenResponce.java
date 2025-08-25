package com.parth.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItenResponce {
    private String itemId;
    private String name;
    private BigDecimal price;;
    private String description;
    private  String categoryId;
    private String categoryName;
    private String imgUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;




    public ItenResponce(String itemId, String name, BigDecimal price, String description, String imgUrl, long id, String name1) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.categoryId = String.valueOf(id);
        this.categoryName = name1;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
