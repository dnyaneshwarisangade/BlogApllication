package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category cat = this.modelMapper.map(categoryDto, Category.class);

        return this.modelMapper.map(this.categoryRepo.save(cat), CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer CategoryId) {

        Category cat = this.categoryRepo.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", CategoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        return this.modelMapper.map(this.categoryRepo.save(cat), CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer CategoryId) {
        Category cat = this.categoryRepo.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", CategoryId));
        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> catList = this.categoryRepo.findAll();
        List<CategoryDto> dtoList = catList.stream().map(c -> this.modelMapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public void deleteCategory(Integer CategoryId) {
        Category cat = this.categoryRepo.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", CategoryId));
        this.categoryRepo.delete(cat);
    }

}
