package org.example.servidorCenso.EE;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.common.errores.ApiError;
import org.example.common.modelos.Persona;
import org.example.servidorCenso.EE.utils.ConstantesRest;
import org.example.common.modelos.ApiRespuesta;
import org.example.servidorCenso.service.PersonaService;

import java.time.LocalDate;
import java.util.List;

@Path(ConstantesRest.PATH_PERSONAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersonas {

    private final PersonaService personaService;

    @Inject
    public RestPersonas(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GET
    public Response getAll() {
        Response response;
        Either<ApiError, List<Persona>> resultado = personaService.getAll();

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

    @POST
    public Response insertPersona(Persona persona) {
        boolean resultado = personaService.insertPersona(persona);
        Response response;
        if (resultado) {
            response = Response.status(Response.Status.OK).entity(resultado).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity(resultado).build();
        }

        return response;
    }

    @DELETE
    @Path(ConstantesRest.PATH_ID)
    public Response personaFuera(@PathParam(ConstantesRest.ID) int id) {
        Response response;
        Either<ApiError, ApiRespuesta> resultado = personaService.deletePersona(id);
        if (resultado.isRight()) {
            response = Response.ok(resultado.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @PUT
    public Response updatePersona(Persona persona) {
        Response response;
        if (personaService.updatePersona(persona)) {
            response = Response.status(Response.Status.CREATED)
                    .entity(new ApiRespuesta(ConstantesRest.PERSONA_ACTUALIZADA))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(ConstantesRest.PERSONA_NO_ENCONTRADA, LocalDate.now()))
                    .build();
        }

        return response;
    }

    @GET
    @Path(ConstantesRest.PATH_FILTROS)
    public Response filtrado(@QueryParam(ConstantesRest.PARAM_LUGAR) String lugar,
                             @QueryParam(ConstantesRest.PARAM_NACIMIENTO) String nacimiento,
                             @QueryParam(ConstantesRest.PARAM_NHIJOS) int nhijos,
                             @QueryParam(ConstantesRest.PARAM_ECIVIL) String ecivil) {
        Response response;
        Either<ApiError, List<Persona>> resultado = personaService.filtrado(lugar, nacimiento, nhijos, ecivil);

        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;

    }

}
