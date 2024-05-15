package com.glos.api.userservice.facade;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.Group;
import com.glos.api.entities.Role;
import com.glos.api.entities.User;
import com.glos.api.userservice.client.RoleAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.exeptions.ResourceNotFoundException;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseDTO.UserFilterRequest;
import com.glos.api.userservice.responseMappers.UserDTOMapper;
import com.glos.api.userservice.responseMappers.UserMapper;
import com.glos.api.userservice.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.glos.api.userservice.utils.MapUtils;

import java.util.Collections;
import java.util.Map;

@Service
public class UserAPIFacade {

    private final UserAPIClient userAPIClient;
    private final RoleAPIClient roleAPIClient;


    public UserAPIFacade(
            UserAPIClient userAPIClient,
            RoleAPIClient roleAPIClient
    ) {
        this.userAPIClient = userAPIClient;
        this.roleAPIClient = roleAPIClient;
    }

    public User getById(Long id) {
        return getUser(userAPIClient.getById(id));
    }
    public User getUserByUsername(String username) {
        return getUser(userAPIClient.getUserByUsername(username));
    }
    public User getEmail(String email) {
        return getUser(userAPIClient.getUserByEmail(email));
    }
    public User getUserByPhoneNumber(String phoneNumber) {
        return getUser(userAPIClient.getUserByPhoneNumber(phoneNumber));
    }

    public ResponseEntity<User> create(User user, String role) {
        user.setRoles(Collections.singletonList(getRole(role)));
        user.setIs_account_non_expired(Constants.DEFAULT_IS_ACCOUNT_NON_EXPIRED);
        user.setIs_account_non_locked(Constants.DEFAULT_IS_ACCOUNT_NON_LOCKED);
        user.setIs_credentials_non_expired(Constants.DEFAULT_IS_CREDENTIALS_NON_EXPIRED);
        user.setIs_enabled(Constants.DEFAULT_IS_ENABLED);
        user.setIs_deleted(Constants.DEFAULT_IS_DELETED);
        ResponseEntity<User> userResponseEntity = userAPIClient.create(user);
        return userResponseEntity;
    }

    private Role getRole(String name) {
        ResponseEntity<Role> response = roleAPIClient.getByName(name);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ResourceNotFoundException("Role not found");
    }

    public ResponseEntity<?> deleteById(Long id) {
        User user = getById(id);
        ResponseEntity<?> response = userAPIClient.delete(user.getId());
        return noContent(response);
    }

    public ResponseEntity<?> updateUser(Long id, User newUser) {
        User user = getById(id);
        newUser.setRoles(newUser.getRoles().stream()
                        .map(x -> roleAPIClient.getByName(x.getName()).getBody())
                        .toList());
        ResponseEntity<?> response = userAPIClient.updateUser(user.getId(), newUser);
        return noContent(response);
    }

    public Page<User> getAllByFilter(UserFilterRequest filter) {
        Map<String, Object> map = MapUtils.map(filter);
        ResponseEntity<Page<User>> response = userAPIClient.getUsersByFilter(map);
        Page<User> page = response.getBody();
        return page;
    }

    public ResponseEntity<?> enabled(String username, boolean isEnabled) {
        User user = getUserByUsername(username);
        user.setIs_account_non_locked(isEnabled);
        ResponseEntity<?> response = updateUser(user.getId(), user);
        return noContent(response);
    }

    public ResponseEntity<?> blocked(String username, boolean blocked) {
        User user = getUserByUsername(username);
        user.setIs_account_non_locked(blocked);
        ResponseEntity<?> response = updateUser(user.getId(), user);
        return noContent(response);
    }

    private <T> ResponseEntity<T> ok(ResponseEntity<T> response) {
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        } else if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    private ResponseEntity<?> noContent(ResponseEntity<?> response) {
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        } else if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    private User getUser(ResponseEntity<User> getUserResponse) {
        if (getUserResponse.getStatusCode().is2xxSuccessful()) {
            return getUserResponse.getBody();
        }
        throw new ResourceNotFoundException("User not found");
    }
}
