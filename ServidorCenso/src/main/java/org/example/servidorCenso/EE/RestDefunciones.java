package org.example.servidorCenso.EE;


import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.servidorCenso.EE.utils.ConstantesRest;
import org.example.servidorCenso.service.DefuncionesService;

@Path(ConstantesRest.PATH_DEFUNCIONES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestDefunciones {

    private final DefuncionesService defuncionesService;

    @Inject
    public RestDefunciones(DefuncionesService defuncionesService) {
        this.defuncionesService = defuncionesService;
    }


    @DELETE
    @Path(ConstantesRest.PATH_ID)
    public Response muerePersona(@PathParam(ConstantesRest.PARAM_ID) int id) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = defuncionesService.muerePersona(id);

        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
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
