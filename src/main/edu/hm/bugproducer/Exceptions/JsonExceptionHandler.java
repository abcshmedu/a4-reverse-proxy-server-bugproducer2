package edu.hm.bugproducer.Exceptions;

import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import edu.hm.bugproducer.Status.MediaServiceResult;
import edu.hm.bugproducer.Status.StatusMgnt;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonExceptionHandler implements
        ExceptionMapper<PropertyBindingException> {

    @Override
    public Response toResponse(PropertyBindingException e) {
        System.err.println("Error" + e.getMessage() + e.getCause());
        StatusMgnt statusMgnt = new StatusMgnt(MediaServiceResult.MSR_INTERNAL_SERVER_ERROR, "Field with the name " + e.getPropertyName() + " doesnt exists!");
        return Response
                .status(statusMgnt.getCode())
                .entity(statusMgnt)
                .build();
    }
}
