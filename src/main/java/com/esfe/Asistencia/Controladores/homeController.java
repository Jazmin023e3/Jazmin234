package com.esfe.Asistencia.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class homeController {

    @RequestMapping
    public String Index(){
        return "home/index";
    }
}
