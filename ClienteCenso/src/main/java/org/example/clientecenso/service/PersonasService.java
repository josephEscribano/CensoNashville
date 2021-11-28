package org.example.clientecenso.service;

import io.vavr.control.Either;
import org.example.clientecenso.dao.DaoPersonas;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;

import javax.inject.Inject;
import java.util.List;

public class PersonasService {
    private final DaoPersonas dao;

    @Inject
    public PersonasService(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Persona>> getAll() {
        return dao.getAll();
    }

    public Either<ApiError,Boolean> insertPersona(Persona persona){
        return dao.insertPersona(persona);
    }


    public Either<ApiError, ApiRespuesta> deletePersona(int id) {
        return dao.deletePersona(id);
    }
}
