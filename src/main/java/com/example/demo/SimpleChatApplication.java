package com.example.demo;

import com.example.demo.chat.ChatEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@Controller
@EnableWebSocket
public class SimpleChatApplication {

    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleChatApplication.class, args);
    }
}
