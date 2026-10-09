package com.learning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/api/hello")
    public Greeting greeting() {
        return new Greeting("Hello, world!");
    }

    public record Greeting(String message) {
    }
}
