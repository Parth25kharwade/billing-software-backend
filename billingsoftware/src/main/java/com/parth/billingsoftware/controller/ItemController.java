package com.parth.billingsoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parth.billingsoftware.io.ItemRequest;
import com.parth.billingsoftware.io.ItenResponce;
import com.parth.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ItemController {
    private final ItemService itemService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/items")
    public ItenResponce addItem(@RequestPart("item")String itemString, @RequestPart("file")MultipartFile file){
        ObjectMapper mapper = new ObjectMapper();
        ItemRequest itemRequest=null;
        try{
            itemRequest=mapper.readValue(itemString.toString(),ItemRequest.class);
            return itemService.addItem(itemRequest,file);
        }catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while parsing the json string"+e.getMessage());
        }
    }
    @GetMapping("/items")
    public List<ItenResponce> getAllItems(){
        return itemService.fetchItems();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/items/{id}")
    public void deleteItem(@PathVariable String id){
        try {
            itemService.deleteItem(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
        }
    }
}
