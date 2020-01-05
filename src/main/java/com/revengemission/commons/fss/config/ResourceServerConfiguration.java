package com.revengemission.commons.fss.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .mvcMatchers("/upload/**").hasAnyAuthority("ROLE_USER", "ROLE_SUPER", "ROLE_ADMIN")
            .mvcMatchers("/protected/**").hasAnyAuthority("ROLE_USER", "ROLE_SUPER", "ROLE_ADMIN")
            .mvcMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer().bearerTokenResolver(new CustomTokenResolver())
            .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        GrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new GrantedAuthoritiesConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
