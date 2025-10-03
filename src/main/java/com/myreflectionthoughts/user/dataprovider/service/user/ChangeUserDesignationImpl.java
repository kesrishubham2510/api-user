package com.myreflectionthoughts.user.dataprovider.service.user;

import com.myreflectionthoughts.user.datamodel.entity.User;
import com.myreflectionthoughts.user.datamodel.role.UserRole;
import com.myreflectionthoughts.user.dataprovider.repository.UserRepository;
import com.myreflectionthoughts.user.exception.UserException;
import com.myreflectionthoughts.user.usecase.ChangeUserDesignation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ChangeUserDesignationImpl implements ChangeUserDesignation<User> {

    private final UserRepository userRepository;

    public ChangeUserDesignationImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
        Promotion can take place between User -> Admin and viceVersa
    */
    @Override
    public ResponseEntity<User> updateUser(String src, String userId) {

        if(!Arrays.stream(UserRole.values()).anyMatch((currentUserRole)-> currentUserRole.name().equalsIgnoreCase(src))){
            throw new UnsupportedOperationException("The operation is not supported");
        }


        User user = userRepository.findById(userId).orElseThrow(()-> new UserException("NOT_FOUND", "The user:- " + userId + ", does not exist"));

        if(StringUtils.equalsIgnoreCase(src, UserRole.ADMIN.name())){
            user.setRole(UserRole.USER);
        }else if(StringUtils.equalsIgnoreCase(src, UserRole.USER.name())){
            user.setRole(UserRole.ADMIN);
        }else{
            throw new UnsupportedOperationException("The operation is not supported");
        }
        user = Optional.of(userRepository.save(user)).orElseThrow(()-> new UserException("UPDATE_FAILED", "Something went wrong while updating the user"));
        return ResponseEntity.ok().body(user);
    }
}
