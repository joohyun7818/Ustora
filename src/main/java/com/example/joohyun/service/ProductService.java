package com.example.joohyun.service;

import com.example.joohyun.entity.Product;
import com.example.joohyun.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findRandomByCount(int count) {
        return productRepository.findRandomProductsNative(count);
    }

    public List<Product> top4productList() {
        List<Product> pList = productRepository.findTop4ByOrderByPid();
        for (Product p : pList) {
            System.out.println(p.toString());
        }
        return pList;
    }

    public List<Product> top5recentList() {
        List<Product> rList = productRepository.findTop5ByOrderByPidDesc();
        return rList;
    }

    public List<Product> top3expensiveList() {
        List<Product> exList = productRepository.findTop3ByOrderByPriceDesc();
        return exList;
    }

    public List<Product> top6expensiveList() {
        List<Product> ex6List = productRepository.findTop6ByOrderByPriceDesc();
        return ex6List;
    }

    public Page<Product> getAllProducts(int pageNum, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
}
