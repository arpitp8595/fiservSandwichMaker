package com.example.fiservpractice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) authentication.getCredentials();
        String userName = (String) usernamePasswordAuthenticationToken.getPrincipal();
        String password = (String) usernamePasswordAuthenticationToken.getCredentials();

        if("arpit".equalsIgnoreCase(userName) && "password".equals(password))
            return new UsernamePasswordAuthenticationToken(userName, "");
        else
            throw new BadCredentialsException("User's Credentials are bad.!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

}
