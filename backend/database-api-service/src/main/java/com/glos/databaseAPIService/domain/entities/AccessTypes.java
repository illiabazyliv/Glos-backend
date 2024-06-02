package com.glos.databaseAPIService.domain.entities;

import java.util.List;
import java.util.Objects;

public enum AccessTypes {
    PROTECTED_R,
    PUBLIC_R,
    PRIVATE_RW,
    PROTECTED_RW,
    PUBLIC_RW;

    private final AccessType accessType;

    AccessTypes() {
        long id = ordinal()+1;
        this.accessType = new AccessType(id, name());
    }

    public static AccessTypes fromId(Long id) {
        AccessTypes[] values = AccessTypes.values();
        for(AccessTypes accessType : values) {
            if (accessType.ordinal()+1 == id.intValue()) {
                return accessType;
            }
        }
        throw new IllegalArgumentException("access type with id %s not found".formatted(id));
    }

    public static AccessTypes fromName(String name) {
        Objects.requireNonNull(name);
        String n = name.toUpperCase().replace("-", "_");
        return valueOf(n);
    }

    public static AccessTypes[] readOnlyTypes() {
        AccessTypes[] readOnlyTypes = new AccessTypes[2];
        System.arraycopy(values(), 0, readOnlyTypes, 0, 2);
        return readOnlyTypes;
    }

    public static AccessTypes[] writeTypes() {
        AccessTypes[] writeTypes = new AccessTypes[3];
        System.arraycopy(values(), 2, writeTypes, 0, 3);
        return writeTypes;
    }

    public static AccessTypes priority(List<AccessType> accessTypes) {
        AccessTypes accessType = PROTECTED_R;
        for(AccessType at : accessTypes) {
            AccessTypes ats = fromName(at.getName());
            if (ats.ordinal() >= accessType.ordinal()) {
                accessType = ats;
            }
        }
        return accessType;
    }

    public AccessType asEntity() {
        return accessType;
    }
}
