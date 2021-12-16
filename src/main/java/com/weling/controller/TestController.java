package com.weling.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "https://api.weling.site")
@RestController
public class TestController {


    @GetMapping("/test")
    public String test(){
        String testStr = "Hi~~";
        System.out.println(testStr);

        return testStr;
    }

    @GetMapping("/signup")
    public String signup(){
        return "hi";

    }

}
