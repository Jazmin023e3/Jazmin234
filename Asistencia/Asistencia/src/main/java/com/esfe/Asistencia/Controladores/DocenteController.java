package esfe.com.Asistencia.Controladores;

import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esfe.com.Asistencia.Modelos.Docente;
import esfe.com.Asistencia.Servicios.Interfaces.IDocenteService;

@Controller
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private IDocenteService docenteService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Docente> docentes = docenteService.buscarTodosPaginados(pageable);
        model.addAttribute("docentes", docentes);

        int totalPages = docentes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "docente/index";
    }

    // ----------- CREAR --------------
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("docente", new Docente());
        model.addAttribute("action", "create");
        return "docente/mant";
    }

    // ----------- EDITAR --------------
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        model.addAttribute("docente", docente);
        model.addAttribute("action", "edit");
        return "docente/mant";
    }

    // ----------- VER (solo lectura) --------------
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        model.addAttribute("docente", docente);
        model.addAttribute("action", "view");
        return "docente/mant";
    }

    // ----------- ELIMINAR (confirmación) --------------
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        model.addAttribute("docente", docente);
        model.addAttribute("action", "delete");
        return "docente/mant";
    }

    // ----------- PROCESAR POST según action --------------
    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute Docente docente, BindingResult result,
                            RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "docente/mant";
        }
        docenteService.crearOEditar(docente);
        redirect.addFlashAttribute("msg", "Docente creado correctamente");
        return "redirect:/docentes";
    }

    @PostMapping("/edit")
    public String saveEditado(@ModelAttribute Docente docente, BindingResult result,
                              RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "edit");
            return "docente/mant";
        }
        docenteService.crearOEditar(docente);
        redirect.addFlashAttribute("msg", "Docente actualizado correctamente");
        return "redirect:/docentes";
    }

    @PostMapping("/delete")
    public String deleteDocente(@ModelAttribute Docente docente, RedirectAttributes redirect) {
        docenteService.eliminarPorId(docente.getId());
        redirect.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/docentes";
    }
}
