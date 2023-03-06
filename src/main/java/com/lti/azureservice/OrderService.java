package com.lti.azureservice;

import com.lti.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> callOrderAPI(String url, OrderDTO orderDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(orderDTO, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public ResponseEntity<String> callOrderStatusAPI(String url){
        return restTemplate.getForEntity(url,String.class);
    }

}
