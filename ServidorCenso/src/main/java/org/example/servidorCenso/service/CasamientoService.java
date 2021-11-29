package org.example.servidorCenso.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.servidorCenso.dao.DaoPersonas;

public class CasamientoService {
    private final DaoPersonas dao;

    @Inject
    public CasamientoService(DaoPersonas dao) {
        this.dao = dao;
    }


    public Either<ApiError, ApiRespuesta> boda(int idhombre, int idmujer) {
        return dao.boda(idhombre, idmujer);
    }
}
