package myRestIIQ6.system.exceptions.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import myRestIIQ6.system.exceptions.generic.ErrorDTO;


public class BasicExceptionMapper {

    public Response buildResponse(Status status, Throwable e){
        return buildResponse(status, e.getMessage());
    }

    public Response buildResponse(Status status, String errorMessage){
        return buildResponse(status.getStatusCode(), errorMessage);
    }

   
    private Response buildResponse(int status, String errorMessage){
        ErrorDTO dto = buildErrorDTO(status, errorMessage);
        return Response.status(status).entity(dto).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    private ErrorDTO buildErrorDTO(int status, String errorMessage){
        ErrorDTO dto = new ErrorDTO();
        dto.setHttpCode(status);
        dto.setMessage(errorMessage);
        return dto;
    }

}