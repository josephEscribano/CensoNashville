package org.example.clientecenso.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import org.example.clientecenso.dao.utils.ConstantesDao;
import org.example.clientecenso.gui.DI;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class DaoPersonas {
    private final DI creator;

    @Inject
    public DaoPersonas(DI creator) {
        this.creator = creator;
    }

    public Either<ApiError, List<Persona>> getAll() {
        Either<ApiError, List<Persona>> listpersonas;
        try {
            Response<List<Persona>> response = creator.apiPersonas(creator.createRetrofit()).getPersonas().execute();
            if (response.isSuccessful()) {
                listpersonas = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(ConstantesDao.APPLICATION_JSON)) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    listpersonas = Either.left(apiError);
                } else {
                    listpersonas = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }

            }
        } catch (IOException e) {
            listpersonas = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return listpersonas;
    }

    public Either<ApiError, Boolean> insertPersona(Persona persona) {
        Either<ApiError, Boolean> resultado;

        try {
            Response<Boolean> response = creator.apiPersonas(creator.createRetrofit()).insertPersona(persona).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (!response.errorBody().contentType().equals(ConstantesDao.APPLICATION_HTML)) {
                    resultado = Either.left(new ApiError(ConstantesDao.LA_PERSONA_TIENE_QUE_SER_SOLTERA, LocalDate.now()));
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }

            }
        } catch (IOException e) {

            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta> deletePersona(int id) {
        Either<ApiError, ApiRespuesta> resultado;

        try {
            Response<ApiRespuesta> response = creator.apiPersonas(creator.createRetrofit()).deletePersona(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError);
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }

            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));

            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta> updatePersona(Persona persona) {
        Either<ApiError, ApiRespuesta> resultado;

        try {
            Response<ApiRespuesta> response = creator.apiPersonas(creator.createRetrofit()).updatePersona(persona).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError);
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }
            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public Either<ApiError, List<Persona>> filtrar(String filtroLugar, String filtroFecha, int filtroNhijos, String filtroEcivil) {
        Either<ApiError, List<Persona>> resultado;

        try {
            Response<List<Persona>> response = creator.apiPersonas(creator.createRetrofit()).filtrado(filtroLugar, filtroFecha, filtroNhijos, filtroEcivil).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError);
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }
            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta> boda(int idhombre, int idmujer) {
        Either<ApiError, ApiRespuesta> resultado;

        try {
            Response<ApiRespuesta> response = creator.apiCasamientos(creator.createRetrofit()).boda(idhombre, idmujer).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError);
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }
            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta> nacimiento(int idpadre, int idmadre, Persona persona) {
        Either<ApiError, ApiRespuesta> resultado;

        try {
            Response<ApiRespuesta> response = creator.apiNacimientos(creator.createRetrofit()).nacimiento(idpadre, idmadre, persona).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError);
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }
            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta> muerePersona(int id) {
        Either<ApiError, ApiRespuesta> resultado;

        try {
            Response<ApiRespuesta> response = creator.apiDefunciones(creator.createRetrofit()).muerePersona(id).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = creator.getGson().fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError);
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.RESPUESTA_INESPERADA, LocalDate.now()));
                }
            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstantesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }
}
