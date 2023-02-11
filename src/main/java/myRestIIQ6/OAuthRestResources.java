package myRestIIQ6;

import sailpoint.rest.oauth.SailPointOAuthRestApplication;


public class OAuthRestResources extends SailPointOAuthRestApplication {

	public OAuthRestResources() {
		super();		
		register(MyOauthAPI.class);
		register(OAuthTokenResourceWrapper.class);
		register(ValidateToken.class);
	}

}