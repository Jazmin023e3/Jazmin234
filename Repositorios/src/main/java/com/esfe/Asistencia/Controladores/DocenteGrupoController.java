package com.esfe.Asistencia.Controladores;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.esfe.Asistencia.Servicios.Interfaces.*;

import com.esfe.Asistencia.Modelos.*;
import com.esfe.Asistencia.Servicios.Interfaces.IDocenteGrupoService;



@Controller
@RequestMapping("/asignaciones")
public class DocenteGrupoController {
    @Autowired
    private IDocenteGrupoService docenteGrupoService;

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private IDocenteService docenteService;

    //--listado--//
    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam( "size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);    

        Page<DocenteGrupo> asignacion = docenteGrupoService.buscarTodosPaginados(pageable);
        model.addAttribute("asignaciones", asignacion);

        int totalPages = asignacion.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        return "asignacion/Index";
    }

    //--crear--//
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("docenteGrupo", new DocenteGrupo());
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        return "asignacion/mant";
    }

    //--editar--//
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
       DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id);
         model.addAttribute("docenteGrupo", docenteGrupo);
         model.addAttribute("docentes", docenteService.obtenerTodos());
         model.addAttribute("grupos", grupoService.obtenerTodos());
         model.addAttribute("action", "edit");
         return "asignacion/mant";
    }

    //--Lectura--//
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id);
        model.addAttribute("docenteGrupo", docenteGrupo);
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "view");
        return "asignacion/mant";
    }

    //--eliminar--//
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
      DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id);
      model.addAttribute("docenteGrupo", docenteGrupo);
      model.addAttribute("docentes", docenteService.obtenerTodos());
      model.addAttribute("grupos", grupoService.obtenerTodos());
      model.addAttribute("action", "delete");
      return "asignacion/mant";
    }

    //--procesar post segun accion--//
    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute DocenteGrupo docenteGrupo , BindingResult result
    , RedirectAttributes redirect , Model model) {
       if (result.hasErrors()) {
           model.addAttribute("docentes", docenteService.obtenerTodos());
           model.addAttribute("grupos", grupoService.obtenerTodos());
           model.addAttribute("action", "create");
           return "asignacion/mant";
       }
       docenteGrupoService.crearOEditar(docenteGrupo);
       redirect.addFlashAttribute("mensaje", "La asignación se ha creado correctamente.");
       return "redirect:/asignaciones";
    }

    @PostMapping ("/edit")
    public String saveEdit(@ModelAttribute DocenteGrupo docenteGrupo , BindingResult result
    , RedirectAttributes redirect , Model model) {
       if (result.hasErrors()) {
           model.addAttribute("docentes", docenteService.obtenerTodos());
           model.addAttribute("grupos", grupoService.obtenerTodos());
           model.addAttribute("action", "edit");
           return "asignacion/mant";
       }
       docenteGrupoService.crearOEditar(docenteGrupo);
       redirect.addFlashAttribute("mensaje", "La asignación se ha editado correctamente.");
       return "redirect:/asignaciones";
    }

    @PostMapping("delete")
    public String deleteDocenteGrupo(@ModelAttribute DocenteGrupo docenteGrupo ,
    RedirectAttributes redirect ) {
       docenteGrupoService.eliminarPorId(docenteGrupo.getId());
       redirect.addFlashAttribute("msg", "asignacion eliminada correctamente");
        
        return "redirect:/asignaciones";
    }
    

}
