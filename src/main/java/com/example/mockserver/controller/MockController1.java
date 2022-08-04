package com.example.mockserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class MockController1 {

    @RequestMapping("/**")
    public String doMock(){
        return "do mock server";
    }
}
