package com.campus.backend.service;

import com.campus.backend.entity.Category;
import com.campus.backend.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    public void create(Category category) {
        categoryMapper.insert(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}