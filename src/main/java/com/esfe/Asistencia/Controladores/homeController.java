package com.esfe.Asistencia.Controladores;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model; // Agregué esta importación para el nuevo método

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class homeController {

    @RequestMapping
    public String Index(){
        return "home/index";
    }
    @GetMapping("login")
    public String mostrarLogin() {
        return "home/formLogin";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";
    }

    // Método agregado para manejar la redirección post-login
    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("message", "¡Bienvenido a tu página de inicio!");
        return "home"; // Esto buscará el archivo home.html
    }
    
}