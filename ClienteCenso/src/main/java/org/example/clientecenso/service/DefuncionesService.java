package org.example.clientecenso.service;

import io.vavr.control.Either;
import org.example.clientecenso.dao.DaoPersonas;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;

import javax.inject.Inject;

public class DefuncionesService {
    private final DaoPersonas dao;

    @Inject
    public DefuncionesService(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<ApiError, ApiRespuesta> muerePersona(int id) {
        return dao.muerePersona(id);
    }
}
