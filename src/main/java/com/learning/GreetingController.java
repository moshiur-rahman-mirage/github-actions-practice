package com.learning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/api/hello")
    public Greeting greeting() {
        return new Greeting("Hello, world!");
    }

    @GetMapping("/api/hello2")
    public Greeting greeting2() {
        return new Greeting("Hello, world 2!");
    }

    public record Greeting(String message) {
    }
}
