package org.example.clientecenso.dao.retrofit;

import org.example.clientecenso.dao.utils.ConstantesDao;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitNacimientos {

    @PUT(ConstantesDao.PATH_NACIMIENTOS)
    Call<ApiRespuesta> nacimiento(@Query(ConstantesDao.IDPADRE) int idpadre, @Query(ConstantesDao.IDMADRE) int idmadre, @Body Persona persona);
}
