package com.glos.accessservice.facade;

import com.glos.accessservice.exeptions.SimpleExceptionBody;
import com.glos.accessservice.exeptions.UserAccessDeniedException;
import com.glos.accessservice.facade.chain.InitAccessChain;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AccessFacade {

    private InitAccessChain initChain;

    public AccessFacade(InitAccessChain initChain) {
        this.initChain = initChain;
    }

    public ResponseEntity<?> available(AccessRequest accessRequest) {
        try {
            accessRequest.setData(new HashMap<>());
            if (initChain.check(accessRequest)) {
                return ResponseEntity.ok().build();
            } else {
                throw new UserAccessDeniedException("user access denied");
            }
        } catch (FeignException ex) {
            return ResponseEntity.status(ex.status()).body(new SimpleExceptionBody(ex.getMessage(), new HashMap<>()));
        }
    }

}
