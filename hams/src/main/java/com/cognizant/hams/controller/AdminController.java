package com.cognizant.hams.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/msg")
    public String getMsg() {
        return "Helloooooooooooo";
    }
}
