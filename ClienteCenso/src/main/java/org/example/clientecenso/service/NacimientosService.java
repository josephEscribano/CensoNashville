package org.example.clientecenso.service;

import io.vavr.control.Either;
import org.example.clientecenso.dao.DaoPersonas;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;

import javax.inject.Inject;

public class NacimientosService {
    private final DaoPersonas dao;

    @Inject
    public NacimientosService(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<ApiError, ApiRespuesta> nacimiento(int idpadre, int idmadre, Persona persona) {
        return dao.nacimiento(idpadre, idmadre, persona);
    }
}
