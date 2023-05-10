package com.bzw.inventoryservice.service;

import com.bzw.inventoryservice.dto.InventoryResponse;
import com.bzw.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skucode){
        return inventoryRepository.findBySkucodeIn(skucode).
                stream().map(inventory -> InventoryResponse.builder()
                        .skucode(inventory.getSkucode()).
                        isInStock(inventory.getQuantity()>0).
                        build()).toList();
    }

}
