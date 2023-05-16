package myRestIIQ6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sailpoint.rest.oauth.SailPointOAuthRestApplication;


public class OAuthRestResources extends SailPointOAuthRestApplication {
	private static final Logger logger = LoggerFactory.getLogger(OAuthRestResources.class);
	public OAuthRestResources() {
		super();
		logger.warn("OAuthRestResources_start");
		logger.info("OAuthRestResources_start");
		System.out.println("OAuthRestResources_start");
		register(CORSFilter.class);
		register(MyOauthAPI.class);
		register(OAuthTokenResourceWrapper.class);
		register(ValidateToken.class);
		System.out.println("OAuthRestResources_finish");
		
	}

}