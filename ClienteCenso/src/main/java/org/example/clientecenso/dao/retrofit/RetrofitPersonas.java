package org.example.clientecenso.dao.retrofit;

import org.example.clientecenso.dao.utils.ConstanesDao;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitPersonas {

    @GET(ConstanesDao.PATH_PERSONAS)
    Call<List<Persona>> getPersonas();

    @POST(ConstanesDao.PATH_PERSONAS)
    Call<Boolean> insertPersona(@Body Persona persona);

    @DELETE(ConstanesDao.PERSONAS_ID)
    Call<ApiRespuesta> deletePersona(@Path(ConstanesDao.ID) int id);

}
