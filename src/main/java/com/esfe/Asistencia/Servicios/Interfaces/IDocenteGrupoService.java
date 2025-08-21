package com.esfe.Asistencia.Servicios.Interfaces;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esfe.Asistencia.Modelos.DocenteGrupo;

public interface IDocenteGrupoService {

    List<DocenteGrupo> obtenerTodos();

    Page<DocenteGrupo> buscarTodosPaginados(Pageable pageable);

    DocenteGrupo buscarPorId(Integer id);

    DocenteGrupo crearOEditar(DocenteGrupo docenteGrupo);

    void eliminarPorId(Integer id);
}
