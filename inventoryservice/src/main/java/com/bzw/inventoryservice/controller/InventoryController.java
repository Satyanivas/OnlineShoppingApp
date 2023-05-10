package com.bzw.inventoryservice.controller;

import com.bzw.inventoryservice.dto.InventoryResponse;
import com.bzw.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //http://localhost:0000/api/inventory?skucode=xx&skucode=yy...
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skucode){
        return inventoryService.isInStock(skucode);
    }
}
