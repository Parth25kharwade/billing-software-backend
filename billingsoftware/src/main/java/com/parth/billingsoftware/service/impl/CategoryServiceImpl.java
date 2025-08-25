package com.parth.billingsoftware.service.impl;

import com.parth.billingsoftware.entity.CategoryEntity;
import com.parth.billingsoftware.io.CategoryRequest;
import com.parth.billingsoftware.io.CategoryResponce;
import com.parth.billingsoftware.repository.CategoryRepository;
import com.parth.billingsoftware.repository.ItemRepository;
import com.parth.billingsoftware.service.CategoryService;
import com.parth.billingsoftware.service.CloudinaryImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepositoty;
    private  final CloudinaryImageService cloudinaryImageService;
    private final ItemRepository itemRepository;
    @Override
    public CategoryResponce add(CategoryRequest request, MultipartFile file) {
        Map map= cloudinaryImageService.upload(file);
        String imageUrl = (String) map.get("url");
        String secureImageUrl = (String) map.get("secure_url");
        CategoryEntity newCategory=convertToEntity(request);
        newCategory.setImgUrl(imageUrl);
        newCategory=categoryRepositoty.save(newCategory);
        return converToResponce(newCategory);
    }

    @Override
    public List<CategoryResponce> read() {
        return categoryRepositoty.findAll()
                .stream()
                .map(categoryEntity -> converToResponce(categoryEntity))
                .collect(Collectors.toList());
    }

    
    @Override
    public void delete(String categoryId) {
        CategoryEntity existingCategory = categoryRepositoty.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

        String imgUrl = existingCategory.getImgUrl();

        if (imgUrl != null && !imgUrl.isBlank()) {
            try {
                boolean imageDeleted = cloudinaryImageService.delet(imgUrl);
                if (!imageDeleted) {
                    System.err.println("Warning: Cloudinary image not found or failed to delete for categoryId: " + categoryId);
                }
            } catch (Exception e) {
                // Log and proceed with DB delete
                System.err.println("Error deleting image on Cloudinary for categoryId: " + categoryId);
                e.printStackTrace();
            }
        } else {
            System.out.println("No image URL to delete for categoryId: " + categoryId);
        }

        categoryRepositoty.delete(existingCategory);
    }


    private CategoryResponce converToResponce(CategoryEntity newCategory) {
      Integer itemCount=itemRepository.countAllByCategoryId(newCategory.getId());
        return CategoryResponce.builder()
              .categoryId(newCategory.getCategoryId())
              .name(newCategory.getName())
              .description(newCategory.getDescription())
              .bgColor(newCategory.getBgColor())
              .imgUrl(newCategory.getImgUrl())
              .createdAt(newCategory.getCreatedAt())
              .updatedAt(newCategory.getUpdatedAt())
              .items(itemCount)
              .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
       return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
