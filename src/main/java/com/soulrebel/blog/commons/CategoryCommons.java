package com.soulrebel.blog.commons;

import com.soulrebel.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class CategoryCommons {


    protected final CategoryRepository categoryRepository;
    protected final ModelMapper modelMapper;

    public static final String CATEGORY = "Category";
    public static final String ID = "id";
    public static final String FAILED_TO_CREATE_CATEGORY = "Failed to create category";

}
