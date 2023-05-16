package myRestIIQ6;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
    	logger.info("cors_filter_called");    	
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
     MultivaluedMap<String, Object> multi = response.getHeaders();
     logger.info("multi:"+multi);
      
        response.getHeaders().add("Access-Control-Allow-Headers",
                "CSRF-Token, X-Requested-By, Authorization, Content-Type");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        logger.info("cors_filter_finished");
    }
}