package com.bzw.productservice.service;

import com.bzw.productservice.dto.ProductRequest;
import com.bzw.productservice.dto.ProductResponse;
import com.bzw.productservice.model.Product;
import com.bzw.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository prep;

    public void createProduct(ProductRequest pr){
        Product p=Product.builder()
                .name(pr.getName())
                .description(pr.getDescription())
                .price(pr.getPrice()).build();

        prep.save(p);
        log.info("Product {} is saved",p.getId());

    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products=prep.findAll();
        return products.stream().map(product->map2proresp(product)).toList();
    }

    private ProductResponse map2proresp(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();
    }
}
