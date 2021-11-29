package org.example.clientecenso.service;

import io.vavr.control.Either;
import org.example.clientecenso.dao.DaoPersonas;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import org.example.common.modelos.Sexo;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CasamientosService {
    private final DaoPersonas dao;

    @Inject
    public CasamientosService(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Persona>> getHombres() {
        Either<ApiError, List<Persona>> hombresFiltrados = dao.getAll();

        if (hombresFiltrados.isRight()) {
            hombresFiltrados = Either.right(hombresFiltrados.get().stream().filter(persona -> persona.getSexo() == Sexo.HOMBRE).collect(Collectors.toList()));
        } else {
            hombresFiltrados = Either.left(hombresFiltrados.getLeft());
        }

        return hombresFiltrados;
    }


    public Either<ApiError, List<Persona>> getMujeres() {
        Either<ApiError, List<Persona>> mujeresFiltradas = dao.getAll();

        if (mujeresFiltradas.isRight()) {
            mujeresFiltradas = Either.right(mujeresFiltradas.get().stream().filter(persona -> persona.getSexo() == Sexo.MUJER).collect(Collectors.toList()));
        } else {
            mujeresFiltradas = Either.left(mujeresFiltradas.getLeft());
        }

        return mujeresFiltradas;
    }

    public Either<ApiError, ApiRespuesta> boda(int idhombre, int idmujer) {
        return dao.boda(idhombre, idmujer);
    }
}
