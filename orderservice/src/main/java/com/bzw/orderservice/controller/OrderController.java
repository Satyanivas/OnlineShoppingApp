package com.bzw.orderservice.controller;


import com.bzw.orderservice.dto.OrderRequest;
import com.bzw.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService oserv;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackmethod")
    public String placeOrder(@RequestBody OrderRequest oreq) throws IllegalAccessException {
        oserv.placeorder(oreq);
        return "order placed successfully";
    }

    public String fallbackmethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return "Oops........something went wrong. Please order after sometime******";
    }
}
