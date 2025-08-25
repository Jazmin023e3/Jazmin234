package com.esfe.Asistencia.Controladores;
import java.util.stream.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.esfe.Asistencia.Modelos.Usuario;
import com.esfe.Asistencia.Modelos.Rol;
import com.esfe.Asistencia.Servicios.Interfaces.IRolService;
import com.esfe.Asistencia.Servicios.Interfaces.IUsuarioService;





@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //--listado--//
    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam( "size") Optional<Integer> size) {
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
        return "usuario/Index";
    }

    @GetMapping("/create")
    public String create(Usuario usuario , Model model) {
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/Create";
    }

    @PostMapping("/save")
    public String save(@RequestParam("rol") Integer rol,  Usuario usuario, BindingResult result ,Model model , RedirectAttributes redirectAttributes) 
    {
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolService.obtenerTodos());
            redirectAttributes.addFlashAttribute("error", "Error al guardar el usuario");
            return "usuarios/create";
        }

    String password = passwordEncoder.encode(usuario.getClave());
      Rol perfil = new Rol ();
      perfil.setId(rol);

      usuario.setStatus(1);
      usuario.agregar(perfil);
      usuario.setClave(password);
      usuarioService.crearOEditar(usuario);
      redirectAttributes.addFlashAttribute("success", "Usuario guardado exitosamente");
      return "redirect:/usuarios";
    }

}
