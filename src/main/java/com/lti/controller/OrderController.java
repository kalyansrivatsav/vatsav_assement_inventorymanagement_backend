package com.lti.controller;

import com.lti.azureservice.OrderService;
import com.lti.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO){
        String url = "https://srivatsav-orders-api.azurewebsites.net/order/create";
        return orderService.callOrderAPI(url,orderDTO);
    }
}
