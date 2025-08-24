package com.esfe.Asistencia.Controladores;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esfe.Asistencia.Modelos.Docente;
import com.esfe.Asistencia.Servicios.Interfaces.IDocenteService;
import com.esfe.Asistencia.Utlidades.PdfGeneratorService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/docentes")    
public class DocenteController {
    @Autowired
    private IDocenteService docenteService;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        PageRequest pageable = PageRequest.of(currentPage, pageSize);

        Page<Docente> docentes = docenteService.buscarTodosPaginados(pageable);
        ((RedirectAttributes) model).addAttribute("docentes", docentes);

        int totalPages = docentes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            ((RedirectAttributes) model).addAttribute("pageNumbers", pageNumbers);
        }

        return "docente/index";
    }

    // ----------- CREAR --------------
    @GetMapping("/create")
    public String create(Model model) {
        ((RedirectAttributes) model).addAttribute("docente", new Docente());
        ((RedirectAttributes) model).addAttribute("action", "create");
        return "docente/mant";
    }

    // ----------- EDITAR --------------
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        ((RedirectAttributes) model).addAttribute("docente", docente);
        ((RedirectAttributes) model).addAttribute("action", "edit");
        return "docente/mant";
    }

    // ----------- VER (solo lectura) --------------
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        ((RedirectAttributes) model).addAttribute("docente", docente);
        ((RedirectAttributes) model).addAttribute("action", "view");
        return "docente/mant";
    }

    // ----------- ELIMINAR (confirmación) --------------
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        Docente docente = docenteService.buscarPorId(id).orElseThrow();
        ((RedirectAttributes) model).addAttribute("docente", docente);
        ((RedirectAttributes) model).addAttribute("action", "delete");
        return "docente/mant";
    }

    // ----------- PROCESAR POST según action --------------
    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute Docente docente, BindingResult result,
                            RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            ((RedirectAttributes) model).addAttribute("action", "create");
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
            ((RedirectAttributes) model).addAttribute("action", "edit");
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


    @GetMapping("/docentePDF")
    public void generarPdf(Model model,HttpServletResponse response) throws Exception {

        //Datos en lista para mostrar en el pdf
        List<Docente>  docentes = docenteService.obtenerTodos();

        //Preparar datos para thymeleaf
        Map<String, Object> data = new HashMap<>();
        data.put("docentes",docentes);

        //Generar el pdf con el nombre de la vista o plantilla de thymeleaf
        byte[] pdfBytes = pdfGeneratorService.generatePdfReport("docente/RPDocente", data);

        //Configurar la respuesta http para descargar o mostrar el pdf
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=docentes.pdf");
        response.setContentLength(pdfBytes.length);

        // escribir bytes en el output stream
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();

    }
}
