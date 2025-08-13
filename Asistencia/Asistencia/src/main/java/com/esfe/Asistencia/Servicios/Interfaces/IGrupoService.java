package esfe.com.Asistencia.Servicios.Interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import esfe.com.Asistencia.Modelos.Grupo;

public interface IGrupoService {
    Page<Grupo> buscarTodos(Pageable pageable);

    List<Grupo> ObtenerTodos();

    Optional<Grupo> buscarPorId(Integer id);

    Grupo crearOeditar(Grupo grupo);

    void eliminarporId(Integer id);
}


