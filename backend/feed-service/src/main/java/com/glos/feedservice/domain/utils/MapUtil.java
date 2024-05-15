package com.glos.feedservice.domain.utils;

import com.glos.api.entities.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapUtil {
    public static Map<String, Object> mapRepositoryFilter(Repository filter) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", filter.getId());
        map.put("rootPath", filter.getRootPath());
        map.put("rootName", filter.getRootName());
        map.put("rootFullName", filter.getRootFullName());
        map.put("owner", filter.getOwner());
        map.put("isDefault", filter.getDefault());
        map.put("displayPath", filter.getDisplayPath());
        map.put("displayName", filter.getDisplayName());
        map.put("displayFullName", filter.getDisplayFullName());
        map.put("description", filter.getDescription());
        map.put("accessTypes", filter.getAccessTypes());
        map.put("comments", filter.getComments());
        map.put("secureCodes", filter.getSecureCodes());
        map.put("tags", filter.getTags());
        map.put("files", filter.getFiles());
        return map;
    }
}
