package org.example.clientecenso.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.clientecenso.dao.utils.ConstanesDao;
import org.example.clientecenso.gui.CreatorRetrofitApi;
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
    private final CreatorRetrofitApi creator;

    @Inject
    public DaoPersonas(CreatorRetrofitApi creator) {
        this.creator = creator;
    }

    public Either<ApiError, List<Persona>> getAll() {
        Either<ApiError,List<Persona>> listpersonas;
        try{
            Response<List<Persona>> response = creator.apiPersonas(creator.createRetrofit()).getPersonas().execute();
            if (response.isSuccessful()){
                listpersonas = Either.right(response.body());
            }else{
                ApiError apiError = creator.getGson().fromJson(response.errorBody().string(),ApiError.class);
                listpersonas = Either.left(apiError);
            }
        } catch (IOException e) {
            listpersonas = Either.left(new ApiError(ConstanesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(),e);
        }

        return listpersonas;
    }

    public Either<ApiError,Boolean> insertPersona(Persona persona){
        Either<ApiError,Boolean> resultado;

        try {
            Response<Boolean> response = creator.apiPersonas(creator.createRetrofit()).insertPersona(persona).execute();

            if (response.isSuccessful()){
                resultado = Either.right(response.body());
            }else{
                resultado = Either.left(new ApiError(ConstanesDao.LA_PERSONA_TIENE_QUE_SER_SOLTERA,LocalDate.now()));
            }
        } catch (IOException e) {

            resultado = Either.left(new ApiError(ConstanesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));
            log.error(e.getMessage(),e);
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta> deletePersona(int id) {
        Either<ApiError, ApiRespuesta> resultado;

        try {
            Response<ApiRespuesta> response = creator.apiPersonas(creator.createRetrofit()).deletePersona(id).execute();

            if (response.isSuccessful()){
                resultado = Either.right(response.body());
            }else{
                ApiError apiError = creator.getGson().fromJson(response.errorBody().string(),ApiError.class);
                resultado = Either.left(apiError);
            }
        } catch (IOException e) {
            resultado = Either.left(new ApiError(ConstanesDao.HA_HABIDO_UN_PROBLEMA_EN_EL_SERVIDOR, LocalDate.now()));

            log.error(e.getMessage(),e);
        }

        return resultado;
    }
}
