package org.example.servidorCenso.EE;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.servidorCenso.EE.utils.ConstantesRest;
import org.example.servidorCenso.service.CasamientoService;


@Path(ConstantesRest.PATH_CASAMIENTOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCasamiento {

    private final CasamientoService casamientoService;

    @Inject
    public RestCasamiento(CasamientoService casamientoService) {
        this.casamientoService = casamientoService;
    }

    @PUT
    public Response boda(@QueryParam(ConstantesRest.IDHOMBRE) int idhombre, @QueryParam(ConstantesRest.IDMUJER) int idmujer) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = casamientoService.boda(idhombre, idmujer);

        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;


    }

}
