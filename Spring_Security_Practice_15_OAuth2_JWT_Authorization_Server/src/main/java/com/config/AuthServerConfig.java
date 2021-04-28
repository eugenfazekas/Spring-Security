package com.config;

import java.util.List;

import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig  extends AuthorizationServerConfigurerAdapter {

	@Value("${password}")
    private String password;

    @Value("${privateKey}")
    private String privateKey;

    @Value("${alias}")
    private String alias;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read")
                	.and()
                .withClient("resourceserver")
                .secret("resourceserversecret");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    	
    	TokenEnhancerChain chain = new TokenEnhancerChain();
    	var tokenEnhancers = List.of(new CustomTokenEnhancer(), jwtAccessTokenConverter());
    	
    	chain.setTokenEnhancers(tokenEnhancers);

        endpoints
          .authenticationManager(authenticationManager)
          .tokenStore(tokenStore())
          .tokenEnhancer(chain);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
		var converter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        new ClassPathResource(privateKey),
                        password.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));

        return converter;
		
	}
    
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	} 
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security.tokenKeyAccess("isAuthenticated()");
	}
	
}
