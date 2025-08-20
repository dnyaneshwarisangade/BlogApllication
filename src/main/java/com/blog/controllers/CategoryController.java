package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
        
        CategoryDto saved_Category=this.categoryService.createCategory(dto);
        return new ResponseEntity<>(saved_Category,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer id,@Valid @RequestBody CategoryDto dto) {
        CategoryDto updated_category= this.categoryService.updateCategory(dto,id);
        
        return new ResponseEntity<>(updated_category,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        this.categoryService.deleteCategory(id);
        
        return new ResponseEntity<>(new ApiResponse("Category with deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id) {
        CategoryDto category=this.categoryService.getCategoryById(id);
        
        return new ResponseEntity<>(category,HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> dtoList=this.categoryService.getAllCategories();
        return new ResponseEntity<>(dtoList,HttpStatus.OK);
    }
    
    
    
    
}
