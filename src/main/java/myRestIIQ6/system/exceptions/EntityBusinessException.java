package myRestIIQ6.system.exceptions;

import javax.ws.rs.core.Response.Status;

import myRestIIQ6.system.exceptions.generic.CoffeeGenericException;




public abstract class EntityBusinessException extends CoffeeGenericException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5696407915051550043L;
	private final Status errorStatus = Status.BAD_REQUEST;

  

    public EntityBusinessException(String messageTemplate, Object[] _parameters) {
        super(messageTemplate, _parameters);
    }

    public EntityBusinessException(String errorMessage){
        super(errorMessage);
    }

    public Status getErrorStatus() {
        return errorStatus;
    }

   
}