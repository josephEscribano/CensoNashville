package org.example.servidorCenso.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import org.example.servidorCenso.dao.DaoPersonas;

import java.util.List;

public class NacimientosService {

    private final DaoPersonas dao;

    @Inject
    public NacimientosService(DaoPersonas dao) {
        this.dao = dao;
    }


    public Either<ApiError, List<Persona>> getSoloPersonas() {
        return dao.getSoloPersonas();
    }

    public Either<ApiError, ApiRespuesta> naceHijo(int idpadre, int idmadre, Persona persona) {
        return dao.naceHijo(idpadre, idmadre, persona);
    }
}
