package myRestIIQ6;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import sailpoint.integration.Base64;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sailpoint.api.SailPointFactory;
import sailpoint.tools.GeneralException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.json.JSONObject;

@Path("/")
public class OAuthTokenResourceWrapper {

	private static final Logger logger = Logger.getLogger(OAuthTokenResourceWrapper.class);

	@Context
	private HttpServletRequest httpRequest;

	@POST
	@Consumes({MediaType.APPLICATION_JSON, "application/scim+json"})
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	@Path("generateToken")
//	public Response getOAuthToken(@FormParam("clientId") String clientId,@FormParam("secret") String secret) {
	//	logger.info("***Entering getOAuthToken******");
	public Response getOAuthToken(@HeaderParam("Authorization") String authHeader) {		
		logger.info("***Entering getOAuthToken******");		
		String output = null;
		HashMap<String, String> responseMap = null;
		String context = httpRequest.getContextPath();
		String url = httpRequest.getRequestURL().toString();
		
		String arr[] = url.split(context);
		String serverName = arr[0];
		logger.info("Server name: " + serverName);

		String targetDomain = serverName + context;
		logger.info("Target Domain: " + targetDomain);
		try {
			Client client = ClientBuilder.newClient();
			MultivaluedMap<String, String> formData = new MultivaluedHashMap();
			formData.add("grant_type", "client_credentials");			
			String saltedSecret = authHeader;
			
			
		
			
			Response response = (Response) client.target(targetDomain + "/oauth2/token"). // token URL to get access
																							// token
					
					request(MediaType.APPLICATION_JSON). // JSON Request Type
					accept("application/scim+json"). // JSON Request Type
					header("Authorization", saltedSecret) // Authorization header goes here
					.post(Entity.form(formData)); // body with grant type
			output = response.readEntity(String.class); // reading response as string format
			if (output.startsWith("<html>")) {

				for (int iCount = 0; iCount < 3; iCount++) {
				//	logger.info("Token not generated for client id: " + clientId);
					Thread.sleep(1000);
					response = (Response) client.target(targetDomain + "/oauth2/token"). // token URL to get access
																							// token

							request(MediaType.APPLICATION_JSON). // JSON Request Type
							header("Authorization", saltedSecret) // Authorization header goes here
							.post(Entity.form(formData)); // body with grant type
					output = response.readEntity(String.class);
					if (!output.startsWith("<html>"))
						break;
				}
			}
			logger.info("Client Validated: Token Generated Successfully: Not Pritned in the Logs: " + output);
			responseMap = convertJSONToMap(output);

			if (responseMap.get("error") != null) {
				logger.error("oAuth Response: " + output);
				return Response.serverError().entity(responseMap).build();

			}

		} catch (JsonGenerationException e) {
			logger.error("JSON Generation Expection: " + e);
			return Response.serverError()
					.entity("JSON Generation Expection: Error Occured while generating token: " + e.getMessage())
					.build();
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: " + e);
			return Response.serverError()
					.entity("JsonMappingException: Error Occured while generating token: " + e.getMessage()).build();
		} catch (IOException e) {
			logger.error("IOException: " + e);
			return Response.serverError().entity("IOException: Error Occured while generating token: " + e.getMessage())
					.build();
		} catch (Exception exp) {
			logger.error("Error Occured while generating token: " + exp);
			return Response.serverError().entity("Error Occured while generating token: " + exp.getMessage()).build();
		}
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(responseMap).build();
	}

	public static HashMap<String, String> convertJSONToMap(String json)
			throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
//Removing invisible chars causing error while converting
		json = json.trim().replaceFirst("\ufeff", "");
// convert JSON string to Map
		map = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
		});

		logger.info("String JSON to Map: Not printing since it has token info: " + map);

		return map;
	}
		

}