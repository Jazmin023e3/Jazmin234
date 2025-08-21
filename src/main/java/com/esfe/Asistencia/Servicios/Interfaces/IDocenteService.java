package com.esfe.Asistencia.Servicios.Interfaces;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esfe.Asistencia.Modelos.Docente;

public interface IDocenteService {
    Page<Docente> buscarTodosPaginados(Pageable pageable);

    List<Docente> obtenerTodos();

    Optional<Docente> buscarPorId(Integer id);

    Docente crearOEditar(Docente docente);

    void eliminarPorId(Integer id);
}
