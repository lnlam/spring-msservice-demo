package com.example.client2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Client2Controller {

    @GetMapping("/callClientTwo")
    public ResponseEntity<String> callSecondClient() throws Exception {
        String responseString = "Hello world, Response from client 2";
        return new ResponseEntity<>(responseString, HttpStatus.OK);
    }
}
