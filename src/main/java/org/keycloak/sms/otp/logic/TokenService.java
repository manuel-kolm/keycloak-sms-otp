package org.keycloak.sms.otp.logic;

import java.util.Random;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TokenService.class);
	private static final Random RANDOM = new Random();

	public void sendToken(AuthenticationFlowContext context) {
		String token = String.format("%6d", RANDOM.nextInt());
		context.getAuthenticationSession().setAuthNote("token", token);
		
		LOG.info("Token \"{}\" generated for user: {}", token, context.getUser().getUsername());
	}
}
