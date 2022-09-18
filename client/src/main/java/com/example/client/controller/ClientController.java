package com.example.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {

    private final LoadBalancerClient loadbalancer;

    private final RestTemplate restTemplate;

    public ClientController (LoadBalancerClient loadbalancer, RestTemplate restTemplate) {
        this.loadbalancer = loadbalancer;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/callClientOne")
    public ResponseEntity<String> callFirstClient() throws Exception {
        String responseString = "Response from client";
        return new ResponseEntity<>(responseString, HttpStatus.OK);
    }

    @GetMapping("/call-c2-from-c1")
    public ResponseEntity<String> callSecondClient() throws Exception {
        try {
            return new ResponseEntity<>(
                    restTemplate.getForObject(getBaseUrl() + "/callClientTwo", String.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    restTemplate.getForObject(getBaseUrl() + "/callClientTwo", String.class), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getBaseUrl() {
        ServiceInstance instance = loadbalancer.choose("CLIENT2");
        return instance.getUri().toString();
    }
}
