package com.esfe.Asistencia.Controladores;


import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class homeController {
    
    @RequestMapping
    public String index() {
        return "Home/Index";
    }   
    @GetMapping("/login")
    public String mostrarLogin()  {
        return "Home/formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request)  {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";
    }

}