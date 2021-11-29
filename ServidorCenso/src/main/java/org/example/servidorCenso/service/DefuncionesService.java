package org.example.servidorCenso.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.servidorCenso.dao.DaoPersonas;

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
