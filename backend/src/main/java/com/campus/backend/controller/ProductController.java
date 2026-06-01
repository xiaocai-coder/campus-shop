package com.campus.backend.controller;

import com.campus.backend.dto.ProductRequest;
import com.campus.backend.entity.Product;
import com.campus.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping("/api/admin/products")
    public Map<String, String> create(@RequestBody ProductRequest request) {
        productService.create(request);
        return Map.of("message", "商品新增成功");
    }

    @PutMapping("/api/admin/products/{id}")
    public Map<String, String> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        productService.update(id, request);
        return Map.of("message", "商品修改成功");
    }

    @DeleteMapping("/api/admin/products/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        productService.delete(id);
        return Map.of("message", "商品删除成功");
    }

    @PutMapping("/api/admin/products/{id}/status")
    public Map<String, String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        productService.updateStatus(id, body.get("status"));
        return Map.of("message", "商品状态修改成功");
    }
}