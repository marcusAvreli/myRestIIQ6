package myRestIIQ6;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.integration.AuthenticationUtil;
import sailpoint.integration.Base64;
import sailpoint.object.Identity;
import sailpoint.object.OAuthClient;
import sailpoint.rest.AuthenticationFilter;
import sailpoint.rest.AuthenticationResult;
import sailpoint.rest.HttpSessionStorage;
import sailpoint.server.Auditor;
import sailpoint.service.LoginService;
import sailpoint.service.oauth.OAuthAccessToken;
import sailpoint.service.oauth.OAuthClientService;
import sailpoint.service.oauth.OAuthTokenExpiredException;
import sailpoint.service.oauth.OAuthTokenValidator;
import sailpoint.tools.GeneralException;
import sailpoint.web.LoginBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

import com.google.gson.Gson;

@Provider
public class ValidateToken extends AuthenticationFilter {
	private static final Logger logger = LoggerFactory.getLogger(ValidateToken.class);

	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Map<String, Object> validateToken(HttpServletRequest req) throws IOException {
		logger.info("validateToken STARTED");
		Map<String, Object> result = null;
		if (isAuthRequest(req)) {			
			result = bearerAuthenticate(req);			
		}
		logger.info("validateToken FINISHED");
		return result;
	}

	

	public boolean isAuthRequest(HttpServletRequest httpRequest) {
		boolean isBearerAuth = false;
		logger.info("isAuthRequest STARTED");
		String authHeader = getAuthHeader(httpRequest);		
		isBearerAuth = AuthenticationUtil.isBearerAuth(authHeader);
		logger.info("isAuthRequest FINISHED");
		return isBearerAuth;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("enter do filter");
		response.setContentType(MediaType.APPLICATION_JSON);
		super.doFilter(request, response, chain);
	}

	public Map<String, Object> bearerAuthenticate(HttpServletRequest httpRequest) {

		logger.info("bearerAuthenticate STARTED");
		boolean success = false;

		Map<String, Object> result = new HashMap<String, Object>();

		AuthenticationResult.Reason reason = AuthenticationResult.Reason.UNSPECIFIED;

		result.put("success", success);
		String authHeader = getAuthHeader(httpRequest);


		OAuthAccessToken token = null;
		SailPointContext ctx = null;
		HttpSession session = null;
		try {
			//ctx = SailPointFactory.getCurrentContext();
			if(null == ctx) {
				ctx = SailPointFactory.createContext("My Context");
			}
			logger.info("Getting Context");

			OAuthTokenValidator validator = new OAuthTokenValidator(ctx);

			String[] parts = null;

			token = validator.authenticate(authHeader);

			if (null != token) {
				String proxyUser = token.getIdentityId();

				Identity user = ctx.getObjectByName(Identity.class, proxyUser);
				OAuthClientService oAuthClientSvc = new OAuthClientService(ctx);
				OAuthClient client = oAuthClientSvc.getClient(token.getClientId());
				logger.info("Proxy Identity: " + user + "Client name: " + client.getName());

				if (user != null) {
					result.put("success", true);
					ctx.setUserName(user.getName());
					session = httpRequest.getSession();
					LoginService.writeIdentitySession(new HttpSessionStorage(session), user);

					Auditor.log("login", user.getName());
					result.put("apiClient", client.getName());
					result.put("proxyUser", user);
					result.put("context", ctx);
				} else {
					result.put("success", false);
					result.put("reason", "Unable to resolve proxy user");
				}

			}
			
			SailPointFactory.releaseContext(ctx);
		} catch (OAuthTokenExpiredException e) {
			reason = AuthenticationResult.Reason.OAUTH_TOKEN_EXPIRED;
			result.put("success", false);
			result.put("reason", reason.toString());
			logger.error("OAuth Token Expired: " + reason.toString());
		} catch (GeneralException localGeneralException) {

			result.put("success", false);
			result.put("reason", localGeneralException.getMessage());
			logger.error("Local general Exp occured: " + localGeneralException);
		} catch (GeneralSecurityException e) {
			result.put("success", false);
			result.put("reason", e.getMessage());
			logger.error("Unable to authenticate using Bearer Authentication: ", e);
		}
		logger.info("bearerAuthenticate FINISHED");
		return result;
	}

	public static String convertToJson(String error) {
		logger.info("convert to json start");
		String strResp = null;
		HashMap<String, String> errResp = new HashMap<String, String>();
		errResp.put("error", error);
		Gson gson = new Gson();
		strResp = gson.toJson(errResp);
		logger.info("convert to json fisnih");
		return strResp;

	}

}