package com.myreflectionthoughts.user.dataprovider.service.auth;

import com.myreflectionthoughts.user.datamodel.entity.User;
import com.myreflectionthoughts.user.datamodel.entity.UserAuth;
import com.myreflectionthoughts.user.dataprovider.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userDetails = null;

        User existingUserByEmail = userRepository.findByEmail(username);
        User existingUserByUsername = userRepository.findByUsername(username);

        if(!Objects.isNull(existingUserByEmail)){
            userDetails = new UserAuth(existingUserByEmail);
        }else if(!Objects.isNull(existingUserByUsername)) {
            userDetails = new UserAuth(existingUserByUsername);
        }

        return userDetails;
    }
}
