package com.faccat.sistemasdistribuidos.g2.jogodistribuido.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/teste")
    public String teste(){
        return "Servico distribuido rodando totalmente em container: " + appName + ".\n";
    }
}
