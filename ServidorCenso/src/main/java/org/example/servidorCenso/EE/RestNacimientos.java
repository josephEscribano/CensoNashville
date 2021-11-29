package org.example.servidorCenso.EE;


import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import org.example.servidorCenso.EE.utils.ConstantesRest;
import org.example.servidorCenso.service.NacimientosService;

import java.util.List;

@Path(ConstantesRest.PATH_NACIMIENTOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestNacimientos {

    private final NacimientosService nacimientosService;

    @Inject
    public RestNacimientos(NacimientosService nacimientosService) {
        this.nacimientosService = nacimientosService;
    }

    @GET
    public Response getSoloPersonas() {
        Response response;
        Either<ApiError, List<Persona>> resultado = nacimientosService.getSoloPersonas();

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

    @PUT
    public Response naceHijo(@QueryParam(ConstantesRest.IDPADRE) int idpadre, @QueryParam(ConstantesRest.IDMADRE) int idmadre, Persona persona) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = nacimientosService.naceHijo(idpadre, idmadre, persona);
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
