package org.example.clientecenso.dao.retrofit;

import org.example.clientecenso.dao.utils.ConstantesDao;
import org.example.common.modelos.ApiRespuesta;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitCasamientos {

    @PUT(ConstantesDao.PATH_CASAMIENTOS)
    Call<ApiRespuesta> boda(@Query(ConstantesDao.IDHOMBRE) int idhombre, @Query(ConstantesDao.ID_MUJER) int idmujer);

}
