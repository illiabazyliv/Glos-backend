package com.glos.accessservice.facade;

import com.glos.accessservice.clients.AuthClient;
import com.glos.accessservice.clients.SecureCodeClient;
import com.glos.accessservice.responseDTO.JwtResponse;
import com.glos.accessservice.responseDTO.SharedEntity;
import com.glos.accessservice.responseDTO.SharedTokenResponse;
import com.glos.accessservice.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SharedFacade
{
    private final AuthClient authClient;

    public SharedFacade(AuthClient authClient)
    {
        this.authClient = authClient;
    }

    public ResponseEntity<SharedTokenResponse> generate(String rootFullName)
    {
        SharedEntity sharedEntity = new SharedEntity(Constants.DEFAULT_EXPIRED_MILLIS, rootFullName);
        JwtResponse response = authClient.generateToken(sharedEntity).getBody();
        SharedTokenResponse tokenResponse = new SharedTokenResponse(response.getAccessToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
