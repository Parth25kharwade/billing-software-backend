package com.parth.billingsoftware.service;

import com.parth.billingsoftware.io.ItemRequest;
import com.parth.billingsoftware.io.ItenResponce;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItenResponce addItem(ItemRequest item, MultipartFile file);
    List<ItenResponce> fetchItems();
    void deleteItem(String id);
}
