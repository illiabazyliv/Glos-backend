package com.glos.accessservice.facade;

import com.glos.accessservice.clients.SecureCodeClient;

public class SharedFacade
{
    private final SecureCodeClient secureCodeClient;

    public SharedFacade(SecureCodeClient secureCodeClient) {
        this.secureCodeClient = secureCodeClient;
    }


}
