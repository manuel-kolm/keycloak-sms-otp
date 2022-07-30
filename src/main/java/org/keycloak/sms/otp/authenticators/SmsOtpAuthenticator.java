package org.keycloak.sms.otp.authenticators;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;

public class SmsOtpAuthenticator implements Authenticator {

	private static final String PHONE_NUMBER_SETUP_TEMPLATE = "";
	private static final String TOKEN_INPUT_TEMPLATE = "";

	@Override
	public void close() {
		// nothing
	}

	@Override
	public void authenticate(AuthenticationFlowContext context) {
		Response response = context.form() //
				.createForm(PHONE_NUMBER_SETUP_TEMPLATE);

		context.challenge(response);
	}

	@Override
	public void action(AuthenticationFlowContext context) {
		String enteredPhoneNumber = context.getHttpRequest().getDecodedFormParameters().getFirst("phoneNumber");
		String enteredToken = context.getHttpRequest().getDecodedFormParameters().getFirst("phoneNumber");

		if (StringUtils.isNoneBlank(enteredPhoneNumber)) {
			challengeTokenInputTemplate(context);
		} else if (StringUtils.isNoneBlank(enteredToken)) {
			AuthenticationSessionModel authSession = context.getAuthenticationSession();
			String token = authSession.getAuthNote("token");
			String ttl = authSession.getAuthNote("ttl");

			if (StringUtils.equals(token, enteredToken) && Long.parseLong(ttl) > System.currentTimeMillis()) {
				context.success();
			} else {
				challengeTokenInputTemplate(context);
			}
		}
	}

	@Override
	public boolean requiresUser() {
		return true;
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		return true;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
		// nothing
	}

	private void challengeTokenInputTemplate(AuthenticationFlowContext context) {
		Response response = context.form() //
				.createForm(TOKEN_INPUT_TEMPLATE);

		context.challenge(response);
	}
}
