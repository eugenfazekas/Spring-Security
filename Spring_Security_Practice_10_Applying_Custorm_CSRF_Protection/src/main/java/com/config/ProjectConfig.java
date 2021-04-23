package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.csrf.CustomCsrfTokenRepository;
import com.filters.CsrfTokenLogger;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

	@Bean
    public UserDetailsService uds() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("mary")
                .password("12345")
                .authorities("READ")
                .build();

        uds.createUser(u1);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.httpBasic();
    	http.csrf().ignoringAntMatchers("/ciao","/db/**");
        http.csrf(c -> {
            c.csrfTokenRepository(customTokenRepository());        
        				});
        http.addFilterAfter(
                new CsrfTokenLogger(),
                CsrfFilter.class).authorizeRequests().antMatchers("/db/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.headers().frameOptions().sameOrigin();
    }
}

//HandlerMappingIntrospector i = new HandlerMappingIntrospector();
//MvcRequestMatcher r = new MvcRequestMatcher(i, "/ciao");
//c.ignoringRequestMatchers(r);

//String pattern = ".*[0-9].*";
//String httpMethod = HttpMethod.POST.name();
//RegexRequestMatcher r = new RegexRequestMatcher(pattern, httpMethod);
//c.ignoringRequestMatchers(r);
