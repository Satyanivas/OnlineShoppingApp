package com.bzw.productservice.controller;


import com.bzw.productservice.dto.ProductRequest;
import com.bzw.productservice.dto.ProductResponse;
import com.bzw.productservice.service.ProductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

   private final ProductService ps;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public void createProduct(@RequestBody ProductRequest pr){
    ps.createProduct(pr);
   }

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   public List<ProductResponse> getAllProducts(){
      return ps.getAllProducts();
   }


}
