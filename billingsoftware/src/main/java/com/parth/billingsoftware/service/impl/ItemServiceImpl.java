package com.parth.billingsoftware.service.impl;

import com.parth.billingsoftware.entity.CategoryEntity;
import com.parth.billingsoftware.entity.ItemEntity;
import com.parth.billingsoftware.io.ItemRequest;
import com.parth.billingsoftware.io.ItenResponce;
import com.parth.billingsoftware.repository.CategoryRepository;
import com.parth.billingsoftware.repository.ItemRepository;
import com.parth.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private  final CloudinaryImageServiceImpl cloudinaryImageService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Override
    public ItenResponce addItem(ItemRequest request, MultipartFile file) {
        Map map=cloudinaryImageService.upload(file);
        ItemEntity newEntity=convertToEntity(request,map);
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: "+request.getCategoryId()));
        newEntity.setCategory(existingCategory);
        newEntity.setImgUrl(map.get("url").toString());
        newEntity=itemRepository.save(newEntity);
        return convertToResponce(newEntity);
    }

    private ItenResponce convertToResponce(ItemEntity newEntity) {
        return ItenResponce.builder()
                .itemId(newEntity.getItemId())
                .name(newEntity.getName())
                .price(newEntity.getPrice())
                .description(newEntity.getDescription())
                .imgUrl(newEntity.getImgUrl())
                .categoryId(newEntity.getCategory().getCategoryId())
                .categoryName(newEntity.getCategory().getName())
                .createdAt(newEntity.getCreatedAt())
                .updatedAt(newEntity.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest item, Map map) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .ImgUrl(map.get("url").toString())
                .build();
    }

    @Override
    public List<ItenResponce> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemEntity -> convertToResponce(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String id) {
        ItemEntity existingItem = itemRepository.findByItemId(id)
                .orElseThrow(()->new RuntimeException("Item Not Found"));
        boolean idDelete = cloudinaryImageService.delet(existingItem.getImgUrl());
        if(idDelete){
            itemRepository.deleteById(existingItem.getId());
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete item" + id);
        }


    }
}