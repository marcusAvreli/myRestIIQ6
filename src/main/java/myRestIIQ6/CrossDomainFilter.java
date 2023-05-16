package myRestIIQ6;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PreMatching
public class CrossDomainFilter implements ContainerResponseFilter {
	private static final Logger logger = LoggerFactory.getLogger(CrossDomainFilter.class);
    /**
     * Add the cross domain data to the output for OPTIONS request (preflight) only
     *
     * @param containerRequest The container request (input)
     * @param containerResponse The container response (output)
     */
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
    	logger.warn("cross_domain_called");
    	System.out.println("cross_domain_called");
        if(containerRequest.getMethod().equals("OPTIONS")) {
        	logger.warn("prefligh_detected");
        	System.out.println("prefligh_detected");
        	containerRequest.getRequestHeaders().add("Access-Control-Allow-Origin", "*");
        	containerRequest.getRequestHeaders().add("Access-Control-Allow-Headers", "CSRF-Token, X-Requested-By, Authorization, Content-Type");
        	containerRequest.getRequestHeaders().add("Access-Control-Allow-Credentials", "true");
        	containerRequest.getRequestHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
               
        }
        
        
        return containerResponse;
    }

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
}