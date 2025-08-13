package esfe.com.Asistencia.Repositorios;
import org.springframework.data.jpa.repository.JpaRepository;

import esfe.com.Asistencia.Modelos.Docente;

public interface IDocenteRepository extends JpaRepository<Docente, Integer>{

    
}