package com.myreflectionthoughts.user.endpoint;

import com.myreflectionthoughts.user.config.RestConstant;
import com.myreflectionthoughts.user.datamodel.request.UpdateSelfRequest;
import com.myreflectionthoughts.user.datamodel.response.ErrorResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import com.myreflectionthoughts.user.datamodel.response.UserDetails;
import com.myreflectionthoughts.user.dataprovider.service.user.ReadSelfImpl;
import com.myreflectionthoughts.user.dataprovider.service.user.UpdateSelfImpl;
import com.myreflectionthoughts.user.usecase.UpdateSelf;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@RequestMapping(RestConstant.API_PREFIX)
public class MeController {

    private final ReadSelfImpl readSelfImpl;
    private final UpdateSelf<UserDetails, UpdateSelfRequest> updateSelfImpl;

    public MeController(ReadSelfImpl readSelfImpl, UpdateSelfImpl updateSelfImpl){
        this.readSelfImpl = readSelfImpl;
        this.updateSelfImpl = updateSelfImpl;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user details are retrieved successfully", headers = {
                    @Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")),
                    @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string")),
                    @Header(name = "Authorization", description = "The token used for identification of the user", schema = @Schema(type = "string")),
            },
                    content = @Content(schema = @Schema(implementation = UserDetails.class))),
            @ApiResponse(responseCode = "401", description = "The identification token is malformed or expired, please refer response for more details", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))},
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(RestConstant.MY_ENDPOINT)
    public ResponseEntity<UserDetails> getMyDetails(HttpServletRequest servletRequest){
        return readSelfImpl.fetchMyDetails(servletRequest);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user details have been updated successfully", headers = {
                    @Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")),
                    @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string")),
                    @Header(name = "Authorization", description = "The token used for identification of the user", schema = @Schema(type = "string")),
            },
                    content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "The user input is contradictory, please refer response for more details",
                    headers = {
                            @Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")),
                            @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))},
                                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "The identification token is malformed or expired, please refer response for more details", headers = {@Header(name = "Content-Type", description = "The format of response", schema = @Schema(type = "string")), @Header(name = "X-request-Id", description = "The UUID of the request", schema = @Schema(type = "string"))},
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping(RestConstant.MY_ENDPOINT)
    public ResponseEntity<UserDetails> updateMyDetails(@RequestBody UpdateSelfRequest updateSelfRequest, HttpServletRequest servletRequest){
        return updateSelfImpl.updateMyData(updateSelfRequest, servletRequest);
    }
}
