package com.glos.accessservice.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum AccessTypes {
    PROTECTED_R,
    PUBLIC_R,
    PRIVATE_RW,
    PROTECTED_RW,
    PUBLIC_RW,
    NONE;

    public final static List<AccessTypes> READ_ONLY_TYPES = List.of(PROTECTED_R, PUBLIC_R);
    public final static List<AccessTypes> READ_WRITE_TYPES = List.of(PRIVATE_RW, PROTECTED_RW, PUBLIC_RW);

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

    public boolean isPrivateType() {
        return this == PRIVATE_RW;
    }

    public boolean isProtectedType() {
        return this == PROTECTED_R || this == PROTECTED_RW;
    }

    public boolean isPublicType() {
        return this == PUBLIC_R || this == PUBLIC_RW;
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
