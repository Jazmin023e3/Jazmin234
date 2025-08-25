package com.esfe.Asistencia.Servicios.Implementaciones;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.esfe.Asistencia.Repositorios.IRolRepository;
import com.esfe.Asistencia.Servicios.Interfaces.IRolService;
import com.esfe.Asistencia.Modelos.Rol;

import org.springframework.stereotype.Service;

@Service
public class RolService implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

}
