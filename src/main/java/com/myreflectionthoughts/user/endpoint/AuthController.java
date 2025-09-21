package com.myreflectionthoughts.user.endpoint;

import com.myreflectionthoughts.user.config.RestConstant;
import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.ErrorResponse;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import com.myreflectionthoughts.user.dataprovider.service.user.LoginUserImpl;
import com.myreflectionthoughts.user.dataprovider.service.user.RegisterUserImpl;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.myreflectionthoughts.user.config.RestConstant.API_PREFIX;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(API_PREFIX)
public class AuthController {

    private final RegisterUserImpl registerUserImpl;
    private final LoginUserImpl loginUserImpl;

    public AuthController(RegisterUserImpl registerUserImpl, LoginUserImpl loginUserImpl){
        this.registerUserImpl = registerUserImpl;
        this.loginUserImpl = loginUserImpl;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user is registered successfully", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))}, content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "The user input is not acceptable, refer response for more details", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))}, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(RestConstant.REGISTRATION_ENDPOINT)
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        return ResponseEntity.ok(registerUserImpl.registerUser(registrationRequest));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user is logged successfully", headers = {
                        @Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")),
                        @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string")),
                        @Header(name = "Authorization", description = "The token used for identification of the user", schema = @Schema(type = "string")),
                    },
                    content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "The user input is not acceptable, refer response for more details", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))}, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(RestConstant.LOGIN_ENDPOINT)
    public ResponseEntity<LoginResponse> register(@RequestBody LoginRequest loginRequest){
        return loginUserImpl.loginUser(loginRequest);
    }
}
