package org.example.clientecenso.dao.retrofit;

import org.example.clientecenso.dao.utils.ConstantesDao;
import org.example.common.modelos.ApiRespuesta;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface RetrofitDefunciones {

    @DELETE(ConstantesDao.PATH_DEFUNCIONES_ID)
    Call<ApiRespuesta> muerePersona(@Path(ConstantesDao.ID) int id);
}
