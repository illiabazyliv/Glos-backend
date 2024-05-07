package com.glos.api.userservice.utils;

import com.glos.api.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapUtil
{
    public static Map<String, Object> mapUserFilter(User filter)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("id", filter.getId());
        map.put("username", filter.getUsername());
        map.put("password_hash", filter.getPassword_hash());
        map.put("email", filter.getEmail());
        map.put("phone_number", filter.getPhone_number());
        map.put("gender", filter.getGender());
        map.put("first_name", filter.getFirst_name());
        map.put("last_name", filter.getLast_name());
        map.put("birthdate", filter.getBirthdate());
        map.put("is_account_non_expired", filter.getIs_account_non_expired());
        map.put("is_account_non_locked", filter.getIs_account_non_locked());
        map.put("is_credentials_non_expired", filter.getIs_credentials_non_expired());
        map.put("is_enabled", filter.getIs_enabled());
        map.put("is_deleted", filter.getIs_deleted());
        map.put("groups", filter.getGroups());
        map.put("roles", filter.getRoles());
        return map;
    }
}
