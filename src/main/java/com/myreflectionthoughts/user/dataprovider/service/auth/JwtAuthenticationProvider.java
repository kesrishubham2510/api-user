package com.myreflectionthoughts.user.dataprovider.service.auth;


import com.myreflectionthoughts.user.datamodel.entity.JwtAuthenticationToken;
import com.myreflectionthoughts.user.datamodel.entity.UserAuth;
import com.myreflectionthoughts.user.utility.JwtUtility;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthService userAuthService;
    private final JwtUtility jwtUtility;

    public JwtAuthenticationProvider(UserAuthService userAuthService, JwtUtility jwtUtility){
        this.jwtUtility = jwtUtility;
        this.userAuthService = userAuthService;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;

        String username = jwtUtility.extractUsername((String)token.getPrincipal());

        UserAuth userDetails =  (UserAuth) userAuthService.loadUserByUsername(username);

        if(Objects.isNull(userDetails)){
            throw new InternalAuthenticationServiceException("Token is Invalid, please login again");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),"", userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
