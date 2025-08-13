package esfe.com.Asistencia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import esfe.com.Asistencia.Modelos.Grupo;
public interface IGrupoRepository extends JpaRepository<Grupo, Integer> {
    

}