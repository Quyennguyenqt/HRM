package com.nqt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/userInfo")
public class MainController {
	
	@GetMapping(path = "/basicauth")
    public String basicauth() {
        return "You are authenticated";
    }
	
	
}
