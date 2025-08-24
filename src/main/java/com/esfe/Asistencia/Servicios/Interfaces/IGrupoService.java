package com.esfe.Asistencia.Servicios.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esfe.Asistencia.Modelos.Grupo;
import com.esfe.Asistencia.Modelos.Usuario;
import com.esfe.Asistencia.Servicios.Implementaciones.UsuarioService;

public interface IGrupoService {

    Page<Grupo> buscarTodos(Pageable pageable);

    List<Grupo> obtenerTodos();

    Optional<Grupo>  buscarPorId(Integer id);

    Grupo crearOeditar(Grupo grupo);

    void eliminarPorId(Integer id);

    Page<UsuarioService> obtenerTodosPaginados(Pageable pageable);

    void crearOeditar(Usuario usuario);

}
