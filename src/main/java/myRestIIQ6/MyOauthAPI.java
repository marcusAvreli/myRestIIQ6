package myRestIIQ6;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import myRestIIQ6.system.exceptions.application.ApplicationDoesNotExistException;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.tools.GeneralException;

@Path("myOAuthAPI")
public class MyOauthAPI {
	private static final Logger logger = Logger.getLogger(MyOauthAPI.class);

	@Context
	private HttpServletRequest httpRequest;
	
	@GET
	@Path("test2")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response test2() throws IOException {		
		logger.info("test2 STARTED");		
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		List<CustomApplication> customApplications = null;
		logger.info("validateToken:"+result);
		boolean success = (Boolean) result.get("success");		
		logger.info("test2 FINISHED");
		throw new ApplicationDoesNotExistException();
		/*SailPointContext ctx = null;
		Connection connection;
		ResultSet result2;
		String sql = "select * from test_custom_application";
		try {
			System.out.println("before get context");
			ctx = SailPointFactory.getCurrentContext();
			System.out.println("get context_1");
			if(null == ctx) {
				System.out.println("get context_2");
				ctx = SailPointFactory.createContext("My Context");
				System.out.println("get context_3");
			}
			System.out.println("get context_4");
			connection = ctx.getJdbcConnection();
			Statement statement = connection.createStatement();
			result2 = statement.executeQuery(sql);
			while(result2.next()) {
				if(null == customApplications) {
					customApplications = new ArrayList<CustomApplication>();
				}
			    String name = ((ResultSet) result2).getString("name");
			    String displayName  = result2.getString("display_Name");
			    String description = result2.getString("description");
			    									
			    int id = result2.getInt("id");
			    CustomApplication customApplication = new CustomApplication(id,name,displayName,description);
			    
			    customApplications.add(customApplication);
			    
			    logger.info("iteratre over result set");
			}
			result2.close();
			statement.close();
			SailPointFactory.releaseContext(ctx);
		} catch (GeneralException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		

		
		

		if (!success) {
			logger.error("Access Restricted: " + result.get("reason"));

			return Response.status(401).type(MediaType.APPLICATION_JSON).entity(result).build();

		}
		//YOUR BUSINESS LOGIC GOES BELOW THIS LINE AND YOU RETURN SUCESS ALONG WITH THE RESPONSE JSON
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(customApplications).build();
		*/
	}


}
