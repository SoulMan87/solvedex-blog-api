package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);
}
