package com.soulrebel.blog.service.impl;

import com.soulrebel.blog.commons.CategoryServiceCommons;
import com.soulrebel.blog.entity.Category;
import com.soulrebel.blog.exception.ResourceNotFoundException;
import com.soulrebel.blog.repository.CategoryRepository;
import com.soulrebel.blog.rest.CategoryDto;
import com.soulrebel.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl extends CategoryServiceCommons implements CategoryService {

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        super (categoryRepository, modelMapper);
    }

    @Override
    public CategoryDto createCategory(final CategoryDto categoryDto) {
        final var category = modelMapper.map (categoryDto, Category.class);
        return Optional.of (categoryRepository.save (category))
                .map (savedCategory -> modelMapper.map (savedCategory, CategoryDto.class))
                .orElseThrow (() -> new RuntimeException (FAILED_TO_CREATE_CATEGORY));
    }

    @Override
    public CategoryDto getCategoryById(final Long id) {
        return categoryRepository.findById (id)
                .map (category -> modelMapper.map (category, CategoryDto.class))
                .orElseThrow (() -> new ResourceNotFoundException (CATEGORY, ID, id));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll ()
                .stream ()
                .map (category -> modelMapper.map (category, CategoryDto.class))
                .toList ();
    }

    @Override
    public CategoryDto updateCategory(final Long id, final CategoryDto categoryDto) {
        final var category = categoryRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException (CATEGORY, ID, id));

        Optional.ofNullable (categoryDto.getName ()).ifPresent (category::setName);
        Optional.ofNullable (categoryDto.getDescription ()).ifPresent (category::setDescription);

        final var updatedCategory = categoryRepository.save (category);
        return modelMapper.map (updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(final Long id) {
        categoryRepository.findById (id)
                .map (category -> {
                    categoryRepository.delete (category);
                    return category;
                })
                .orElseThrow (() -> new ResourceNotFoundException (CATEGORY, ID, id));
    }
}
