package com.campus.backend.controller;

import com.campus.backend.entity.Category;
import com.campus.backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping("/api/admin/categories")
    public Map<String, String> create(@RequestBody Category category) {
        categoryService.create(category);
        return Map.of("message", "分类新增成功");
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Map.of("message", "分类删除成功");
    }
}