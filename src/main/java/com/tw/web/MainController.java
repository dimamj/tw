package com.tw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("*")
public class MainController {

    @GetMapping
    public String page(Map<String, Object> modal){
        return "main";
    }
}
