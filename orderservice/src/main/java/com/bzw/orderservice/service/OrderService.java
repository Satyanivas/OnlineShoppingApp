package com.bzw.orderservice.service;

import com.bzw.orderservice.config.WebClientConfig;
import com.bzw.orderservice.dto.InventoryResponse;
import com.bzw.orderservice.dto.OrderLineItemsDto;
import com.bzw.orderservice.dto.OrderRequest;
import com.bzw.orderservice.event.OrderPlacedEvent;
import com.bzw.orderservice.model.Order;
import com.bzw.orderservice.model.OrderLineItems;
import com.bzw.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orepo;

    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer ;

    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;



    public void placeorder(OrderRequest or) throws IllegalAccessException {

        Order order=new Order();
        order.setOrdernumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems=or.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skucodes=order.getOrderLineItems().stream().
                map(OrderLineItems::getSkucode).toList();

        //call inventory service..



        Span inventoryservicelookup=tracer.nextSpan().name("inventoryserviceLOOKUP");

        try(Tracer.SpanInScope spanInScope= tracer.withSpan(inventoryservicelookup.start())){
            InventoryResponse[] inventoryResponsesArray=webClientBuilder.build().get().uri("http://inventoryservice/api/inventory",
                            uriBuilder->uriBuilder.queryParam("skucode",skucodes).build()).retrieve().
                    bodyToMono(InventoryResponse[].class).block();


            boolean allProductsInStock= Arrays.stream(inventoryResponsesArray)
                    .allMatch(InventoryResponse::isInStock);

            if(allProductsInStock){
                orepo.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrdernumber()));
            }else{
                throw new IllegalAccessException("************EMPTY STOCK***********");
            }

        }finally{
            inventoryservicelookup.end();
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems oli=new OrderLineItems();
        oli.setPrice(orderLineItemsDto.getPrice());
        oli.setQuantity(orderLineItemsDto.getQuantity());
        oli.setSkucode(orderLineItemsDto.getSkucode());

        return oli;
    }
}
