package com.glos.api.authservice.util.security;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.exception.ResponseEntityException;
import com.glos.api.authservice.mapper.UserDetailsMapper;
import com.glos.api.entities.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAPIClient userAPIClient;
    private final UserDetailsMapper userDetailsMapper;

    public UserDetailsServiceImpl(
            UserAPIClient userAPIClient,
            UserDetailsMapper userDetailsMapper
    ) {
        this.userAPIClient = userAPIClient;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ResponseEntity<User> response = userAPIClient.getByUsername(username);
        User user = getUser(response);
        return userDetailsMapper.toDto(user);
    }

    private User getUser(ResponseEntity<User> getUserResponse)
            throws UsernameNotFoundException {
        if (getUserResponse.getStatusCode().is2xxSuccessful()) {
            return getUserResponse.getBody();
        } else if (getUserResponse.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new UsernameNotFoundException("Username not found");
        } else if (getUserResponse.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        }
        throw new ResponseEntityException(getUserResponse);
    }
}
