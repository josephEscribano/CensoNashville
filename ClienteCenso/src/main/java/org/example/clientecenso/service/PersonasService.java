package org.example.clientecenso.service;

import io.vavr.control.Either;
import org.example.clientecenso.dao.DaoPersonas;
import org.example.clientecenso.dao.utils.ConstantesDao;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.EstadoCivil;
import org.example.common.modelos.Persona;

import javax.inject.Inject;
import java.time.LocalDate;
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

    public Either<ApiError, Boolean> insertPersona(Persona persona) {
        Either<ApiError, Boolean> control;
        if (persona.getEstadoCivil() != EstadoCivil.SOLTERO) {
            control = Either.left(new ApiError(ConstantesDao.LA_PERSONA_TIENE_QUE_SER_SOLTERA, LocalDate.now()));
            return control;
        } else {

        }
        return dao.insertPersona(persona);

    }


    public Either<ApiError, ApiRespuesta> deletePersona(int id) {
        return dao.deletePersona(id);
    }

    public Either<ApiError, ApiRespuesta> updatePersona(Persona persona) {
        return dao.updatePersona(persona);
    }

    public Either<ApiError, List<Persona>> filtrar(String filtroLugar, String filtroFecha, int filtroNhijos, String filtroEcivil) {
        return dao.filtrar(filtroLugar, filtroFecha, filtroNhijos, filtroEcivil);
    }
}
