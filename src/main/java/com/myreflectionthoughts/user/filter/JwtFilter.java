package com.myreflectionthoughts.user.filter;

import com.myreflectionthoughts.user.datamodel.entity.JwtAuthenticationToken;
import com.myreflectionthoughts.user.utility.AppUtility;
import com.myreflectionthoughts.user.utility.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;
    private final AuthenticationProvider authenticationProvider;

    public JwtFilter(JwtUtility jwtUtility, AuthenticationProvider authenticationProvider){
        this.jwtUtility = jwtUtility;
        this.authenticationProvider = authenticationProvider;
    }

    /*
      --> If the request does not contain any token
          --> Ask user to login
      --> If token exists, validate it
          --> If expired
            --> say Expired
            --> FE will handle refreshing the jwt token
          --> any other exception scenario, ask to login
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            String errorMessage = AppUtility.getJsonMessageBody("ErrorResponse");
            errorMessage = errorMessage.replace("${message}", "Authorization header with `Bearer ` scheme is required");
            buildErrorResponse(response, 401, errorMessage);
            return;
        }else{

            try{

                String token =  jwtUtility.extractToken(authorizationHeader);
                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
                Authentication authResult = authenticationProvider.authenticate(authenticationToken);

                if(authResult.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                }

            }catch (ExpiredJwtException expiredJwtException){
                String errorMessage = AppUtility.getJsonMessageBody("ErrorResponse");
                errorMessage = errorMessage.replace("${message}", "Token Expired");
                buildErrorResponse(response, 401, errorMessage);
                return;
            }catch (JwtException | InternalAuthenticationServiceException jwtValidationException){
                String errorMessage = AppUtility.getJsonMessageBody("ErrorResponse");
                errorMessage = errorMessage.replace("${message}", jwtValidationException.getMessage()+"\n Please login again");
                buildErrorResponse(response, 401, errorMessage);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/api/users/register")
                || path.equals("/api/users/login")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui");
    }

    private void buildErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write(errorMessage);
        response.setContentType(MediaType.APPLICATION_JSON.getType());
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }


}

