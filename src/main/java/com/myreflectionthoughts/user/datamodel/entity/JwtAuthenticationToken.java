package com.myreflectionthoughts.user.datamodel.entity;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String jwtToken;

    public JwtAuthenticationToken(String jwtToken){
        super(null);
        this.jwtToken = jwtToken;
    }

    @Override
    public @Nullable Object getCredentials() {
        return jwtToken;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return jwtToken;
    }
}
