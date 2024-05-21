package com.glos.accessservice.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth")
public interface AuthClient {
}
