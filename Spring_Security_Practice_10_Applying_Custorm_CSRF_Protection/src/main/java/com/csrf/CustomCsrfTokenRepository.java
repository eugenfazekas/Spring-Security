package com.csrf;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import com.entities.Token;
import com.filters.CsrfTokenLogger;
import com.repository.JpaTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

public class CustomCsrfTokenRepository implements CsrfTokenRepository {

	@Autowired
	private JpaTokenRepository jpaTokenRepository;
	
	private Logger logger = Logger.getLogger(CustomCsrfTokenRepository.class.getName());

	@Override
	public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
		String uuid = UUID.randomUUID().toString();
		return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
	}

	@Override
	public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
		Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);
		String checkedToken = null;

		try {
			checkedToken = csrfToken.getToken();
		}catch(NullPointerException e) {
			logger.info("Token is not present " + e);
		}
	
		if(existingToken.isPresent() && checkedToken != null) {
			jpaTokenRepository.updateTokenByIdentifier(identifier, checkedToken);
			
		}else if (identifier !=  null && checkedToken != null) {
			Token token = new Token(); 
			token.setIdentifier(identifier);
			token.setToken(checkedToken);
			jpaTokenRepository.save(token);		
		} else {
			return ;
			}
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
		String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
		Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

		if (existingToken.isPresent()) {
			Token token = existingToken.get();
			return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
		}
		return null;
	}
}
