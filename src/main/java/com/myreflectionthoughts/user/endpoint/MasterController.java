package com.myreflectionthoughts.user.endpoint;

import com.myreflectionthoughts.user.config.RestConstant;
import com.myreflectionthoughts.user.datamodel.entity.User;
import com.myreflectionthoughts.user.dataprovider.service.user.ChangeUserDesignationImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestConstant.API_PREFIX)
public class MasterController {

    private final ChangeUserDesignationImpl changeDesignationImpl;

    public MasterController(ChangeUserDesignationImpl changeDesignationImpl){
        this.changeDesignationImpl = changeDesignationImpl;
    }

    @PatchMapping("/master")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<User> changeDesignation(@RequestParam("curr") String sourceDesignation, @RequestParam("userId") String userId){
        return changeDesignationImpl.updateUser(sourceDesignation, userId);
    }
}
