package org.example.servidorCenso.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import org.example.servidorCenso.dao.DaoPersonas;

import java.util.List;

public class PersonaService {

    private final DaoPersonas dao;

    @Inject
    public PersonaService(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Persona>> getAll() {
        return dao.getAll();
    }

    public boolean insertPersona(Persona persona) {
        return dao.insertPersona(persona);
    }

    public Either<ApiError, ApiRespuesta> deletePersona(int id) {
        return dao.deletePersona(id);
    }


    public boolean updatePersona(Persona persona) {
        return dao.updatePersona(persona);
    }

    public Either<ApiError, List<Persona>> filtrado(String lugar, String nacimiento, int nhijos, String ecivil) {
        return dao.filtrado(lugar, nacimiento, nhijos, ecivil);
    }
}
