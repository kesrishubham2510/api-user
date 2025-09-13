package com.myreflectionthoughts.user.endpoint;

import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.ErrorResponse;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.myreflectionthoughts.user.config.RestConstant.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX)
public class AuthController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user is registered successfully", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))}, content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "The user input is not acceptable, refer response for more details", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))}, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        return ResponseEntity.ok(new RegistrationResponse());
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
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> register(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(new LoginResponse());
    }
}
