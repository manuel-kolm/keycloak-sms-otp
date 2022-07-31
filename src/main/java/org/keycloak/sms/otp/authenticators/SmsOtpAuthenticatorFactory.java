package org.keycloak.sms.otp.authenticators;

import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class SmsOtpAuthenticatorFactory implements AuthenticatorFactory {

	private static final String PROVIDER_ID = "sms-authenticator";
	private static final String DISPLAY_TYPE = "SMS Authentication";
	private static final String OTP = "otp";
	
	private SmsOtpAuthenticator authenticator;

	@Override
	public Authenticator create(KeycloakSession session) {
		if (authenticator == null) {
			authenticator = new SmsOtpAuthenticator();
		}
		
		return authenticator;
	}

	@Override
	public void init(Scope config) {
		// nothing
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
		// nothing
	}

	@Override
	public void close() {
		// nothing
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public String getDisplayType() {
		return DISPLAY_TYPE;
	}

	@Override
	public String getReferenceCategory() {
		return OTP;
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		return REQUIREMENT_CHOICES;
	}

	@Override
	public boolean isUserSetupAllowed() {
		return true;
	}

	@Override
	public String getHelpText() {
		return "Validates an OTP sent via SMS to the users mobile phone.";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return null;
	}

}
