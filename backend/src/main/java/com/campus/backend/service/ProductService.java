package com.campus.backend.service;

import com.campus.backend.dto.ProductRequest;
import com.campus.backend.entity.Product;
import com.campus.backend.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> findAll() {
        return productMapper.findAll();
    }

    public void create(ProductRequest request) {
        Product product = new Product();
        product.setCategoryId(request.getCategoryId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setIcon(request.getIcon());
        product.setDescription(request.getDescription());
        product.setStatus(request.getStatus());

        productMapper.insert(product);
    }

    public void update(Long id, ProductRequest request) {
        Product product = new Product();
        product.setId(id);
        product.setCategoryId(request.getCategoryId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setIcon(request.getIcon());
        product.setDescription(request.getDescription());
        product.setStatus(request.getStatus());

        productMapper.update(product);
    }

    public void delete(Long id) {
        productMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        productMapper.updateStatus(id, status);
    }
}