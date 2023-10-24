package com.soulrebel.blog.controller;

import com.soulrebel.blog.rest.CategoryDto;
import com.soulrebel.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.soulrebel.blog.commons.Constants.CATEGORY_DELETED;
import static com.soulrebel.blog.commons.Constants.MESSAGE;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController implements ICategoryController {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<CategoryDto> createCategory(final CategoryDto categoryDto) {
        return new ResponseEntity<> (categoryService.createCategory (categoryDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryDto> getCategoryById(final Long id) {
        return new ResponseEntity<> (categoryService.getCategoryById (id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteCategory(final Long id) {
        categoryService.deleteCategory (id);
        return new ResponseEntity<> (Map.of (MESSAGE, CATEGORY_DELETED), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<> (categoryService.getAllCategories (), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(final Long id, final CategoryDto categoryDto) {
        return new ResponseEntity<> (categoryService.updateCategory (id, categoryDto), HttpStatus.OK);
    }
}
