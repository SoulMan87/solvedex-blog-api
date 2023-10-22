package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(final CategoryDto categoryDto);

    CategoryDto getCategoryById(final Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(final Long id, final CategoryDto categoryDto);

    void deleteCategory(final Long id);
}
