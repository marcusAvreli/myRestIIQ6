package myRestIIQ6.system.exceptions.generic;

@SuppressWarnings("serial")
public class CoffeeGenericException extends RuntimeException{

    private String messageTemplate;

    private Object[] parameters;

    public CoffeeGenericException(String messageTemplate, Object[] _parameters){
        this.messageTemplate = messageTemplate;
        this.parameters = _parameters;
    }

    public CoffeeGenericException(){
        super();
    }

    public CoffeeGenericException(String message){
        super(message);
    }

    public CoffeeGenericException(Throwable cause){
        super(cause);
    }

    public CoffeeGenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public Object[] getParameters() {
        return parameters;
    }
}