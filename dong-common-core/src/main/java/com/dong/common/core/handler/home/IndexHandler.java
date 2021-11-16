package com.dong.common.core.handler.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class IndexHandler {
    @GetMapping("/index")
    public String index() {
        return "adsfasdf";
    }
}
