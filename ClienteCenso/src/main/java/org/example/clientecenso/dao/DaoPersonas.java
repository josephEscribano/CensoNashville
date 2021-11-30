package org.example.clientecenso.dao;

import com.google.gson.Gson;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import org.example.clientecenso.dao.retrofit.RetrofitCasamientos;
import org.example.clientecenso.dao.retrofit.RetrofitDefunciones;
import org.example.clientecenso.dao.retrofit.RetrofitNacimientos;
import org.example.clientecenso.dao.retrofit.RetrofitPersonas;
import org.example.clientecenso.dao.utils.ConstantesDao;
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

    private final RetrofitPersonas retrofitPersonas;
    private final RetrofitCasamientos retrofitCasamientos;
    private final RetrofitDefunciones retrofitDefunciones;
    private final RetrofitNacimientos retrofitNacimientos;
    private final Gson gson;

    @Inject
    public DaoPersonas(RetrofitPersonas retrofitPersonas, RetrofitCasamientos retrofitCasamientos, RetrofitDefunciones retrofitDefunciones, RetrofitNacimientos retrofitNacimientos, Gson gson) {
        this.retrofitPersonas = retrofitPersonas;

        this.retrofitCasamientos = retrofitCasamientos;
        this.retrofitDefunciones = retrofitDefunciones;
        this.retrofitNacimientos = retrofitNacimientos;
        this.gson = gson;
    }

    public Either<ApiError, List<Persona>> getAll() {
        Either<ApiError, List<Persona>> listpersonas;
        try {
            Response<List<Persona>> response = retrofitPersonas.getPersonas().execute();
            if (response.isSuccessful()) {
                listpersonas = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(ConstantesDao.APPLICATION_JSON)) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
            Response<Boolean> response = retrofitPersonas.insertPersona(persona).execute();

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
            Response<ApiRespuesta> response = retrofitPersonas.deletePersona(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
            Response<ApiRespuesta> response = retrofitPersonas.updatePersona(persona).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
            Response<List<Persona>> response = retrofitPersonas.filtrado(filtroLugar, filtroFecha, filtroNhijos, filtroEcivil).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
            Response<ApiRespuesta> response = retrofitCasamientos.boda(idhombre, idmujer).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
            Response<ApiRespuesta> response = retrofitNacimientos.nacimiento(idpadre, idmadre, persona).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
            Response<ApiRespuesta> response = retrofitDefunciones.muerePersona(id).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.parse(ConstantesDao.APPLICATION_JSON))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
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
