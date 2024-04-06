package com.yeonnex.catalogservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    String getGreeting() {
        return "도서 카탈로그에 오신 것을 환영합니다!";
    }
}
