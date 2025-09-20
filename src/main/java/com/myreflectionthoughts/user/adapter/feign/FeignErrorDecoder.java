package com.myreflectionthoughts.user.adapter.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.user.exception.AuthServiceException;
import com.myreflectionthoughts.user.exception.UserException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.Charsets;

import java.io.IOException;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public FeignErrorDecoder(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {

        try{

            String body = new String(response.body().asInputStream().readAllBytes(), Charsets.UTF_8);

            JsonNode jsonNode = this.objectMapper.readTree(body);

            if(jsonNode.has("error") && jsonNode.has("message") && jsonNode.has("nextStep")){

                AuthServiceException authServiceException = new AuthServiceException();
                authServiceException.setError(jsonNode.get("error").asText());
                authServiceException.setMessage(jsonNode.get("message").asText());
                authServiceException.setNextStep(jsonNode.get("nextStep").asText());

                return  authServiceException;
            }else if(jsonNode.has("message")){
                UserException userException = new UserException();
                userException.setMessage(jsonNode.get("message").asText());

                return userException;
            }else{
                return new UserException("Something went wrong");
            }
        }catch (IOException exception) {
            throw new UserException("Exception occurred while decoding error");
        }

    }
}
