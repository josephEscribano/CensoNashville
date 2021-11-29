package org.example.clientecenso.dao.retrofit;

import org.example.clientecenso.dao.utils.ConstantesDao;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import org.example.common.utils.ConstantesCommon;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitPersonas {

    @GET(ConstantesDao.PATH_PERSONAS)
    Call<List<Persona>> getPersonas();

    @POST(ConstantesDao.PATH_PERSONAS)
    Call<Boolean> insertPersona(@Body Persona persona);

    @DELETE(ConstantesDao.PERSONAS_ID)
    Call<ApiRespuesta> deletePersona(@Path(ConstantesDao.ID) int id);

    @PUT(ConstantesDao.PATH_PERSONAS)
    Call<ApiRespuesta> updatePersona(@Body Persona persona);

    @GET(ConstantesDao.PERSONAS_FILTROS)
    Call<List<Persona>> filtrado(@Query(ConstantesCommon.PARAM_LUGAR) String lugar,
                                 @Query(ConstantesCommon.PARAM_NACIMIENTO) String nacimiento,
                                 @Query(ConstantesCommon.PARAM_NHIJOS) int nhijos,
                                 @Query(ConstantesCommon.PARAM_ECIVIL) String ecivil);

}
