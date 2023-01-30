package com.example.fiservpractice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicSecurityFiletChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic()
                .and()
                .securityMatcher(request -> {
                    String auth = request.getHeader("Authorization");
                    //return auth == null || auth.startsWith("Basic"); // alternate price of code with same functionality
                    return auth == null || !auth.startsWith("Bearer");
                })
                //.authorizeHttpRequests()
                //.requestMatchers("/PlaceOrder/getOrderDetails")
                //.permitAll()
                //.and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain jwtSecurityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .oauth2ResourceServer()
                .jwt(jwtConfigurer -> {
                    try {
                        jwtConfigurer.authenticationManager(new ProviderManager(new JwtAuthenticationProvider(
                                NimbusJwtDecoder.withPublicKey((RSAPublicKey) KeyFactory.getInstance("RSA")
                                                                       .generatePublic(new X509EncodedKeySpec(getPublicKey("-----BEGIN PUBLIC KEY-----\n"
                                                                                                                           + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu1SU1LfVLPHCozMxH2Mo\n"
                                                                                                                           + "4lgOEePzNm0tRgeLezV6ffAt0gunVTLw7onLRnrq0/IzW7yWR7QkrmBL7jTKEn5u\n"
                                                                                                                           + "+qKhbwKfBstIs+bMY2Zkp18gnTxKLxoS2tFczGkPLPgizskuemMghRniWaoLcyeh\n"
                                                                                                                           + "kd3qqGElvW/VDL5AaWTg0nLVkjRo9z+40RQzuVaE8AkAFmxZzow3x+VJYKdjykkJ\n"
                                                                                                                           + "0iT9wCS0DRTXu269V264Vf/3jvredZiKRkgwlL9xNAwxXFg0x/XFw005UWVRIkdg\n"
                                                                                                                           + "cKWTjpBP2dPwVZ4WWC+9aGVd+Gyn1o0CLelf4rEjGoXbAAEgAqeGUxrcIlbjXfbc\n"
                                                                                                                           + "mwIDAQAB\n"
                                                                                                                           + "-----END PUBLIC KEY-----"))))
                                        .build())));
                    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                })
                .and()
                .build();
    }

    private byte[] getPublicKey (String key) {
        return Base64.getMimeDecoder()
                .decode(key.replace("-----BEGIN PUBLIC KEY-----", "")
                                .replace("-----END PUBLIC KEY-----", ""));
    }

}
