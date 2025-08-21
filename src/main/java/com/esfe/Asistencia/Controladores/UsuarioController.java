package com.esfe.Asistencia.Controladores;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.esfe.Asistencia.Modelos.Rol;
import com.esfe.Asistencia.Modelos.Usuario;
import com.esfe.Asistencia.Servicios.Interfaces.IRolService;
import com.esfe.Asistencia.Servicios.Interfaces.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    // Use the correct class for password encoding
    @Autowired
    private PasswordEncoder passwordEncoder; 

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Usuario> usuarios = usuarioService.obtenerTodosPaginados(pageable);
        model.addAttribute("usuarios", usuarios);

        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "usuario/index";
    }

    @GetMapping("/create")
    public String create(Usuario usuario, Model model){
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam("rol") Integer rol, Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(usuario);
            model.addAttribute("roles", rolService.obtenerTodos());
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "usuario/create";
        }

        // The password will now be encoded correctly because `passwordEncoder` is of type `PasswordEncoder`
        String password = passwordEncoder.encode(usuario.getClave()); 
        
        Rol perfil = new Rol();
        perfil.setId(rol);

        usuario.setStatus(1);
        usuario.agregar(perfil);
        usuario.setClave(password);
        usuarioService.crearOEditar(usuario);
        attributes.addFlashAttribute("msg", "Usuario creado correctamente");
        return "redirect:/usuarios";
    }
}