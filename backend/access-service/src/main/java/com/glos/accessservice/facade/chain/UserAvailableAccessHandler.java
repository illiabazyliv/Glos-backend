package com.glos.accessservice.facade.chain;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.AccessTypes;
import com.glos.accessservice.entities.Group;
import com.glos.accessservice.entities.User;
import com.glos.accessservice.exeptions.UserAccessDeniedException;
import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserAvailableAccessHandler extends AccessHandler {
    private final AccessTypeApiClient accessTypeApiClient;

    public UserAvailableAccessHandler(AccessTypeApiClient accessTypeApiClient) {
        this.accessTypeApiClient = accessTypeApiClient;
    }

    @Override
    public boolean check(AccessRequest request) throws UserAccessDeniedException {
        super.check(request);
        final Map<String, Object> data = request.getData();
        List<AccessType> accessTypes = (List<AccessType>) data.get("accessTypes");
        AccessTypes accessType = AccessTypes.priority(accessTypes);
        User owner = (User) data.get("owner");
        User user = (User) data.get("user");

        if (accessType.isPrivateType())
            throwUserAccessDenied("user access denied", request.getData());

        if (AccessTypes.READ_ONLY_TYPES.contains(accessType) && !request.isReadOnly())
            throwUserAccessDenied("user access denied", request.getData());

        if (!accessType.isPublicType()) {
            final List<Group> ownerGroups = owner.getGroups();
            final List<Group> groupsWithUser = ownerGroups.stream()
                    .filter(x -> x.getUsers().contains(user))
                    .toList();
            final Map<Group, AccessTypes> groupAccessTypes = groupsWithUser.stream()
                    .collect(Collectors.toMap(k -> k, v -> AccessTypes.priority(v.getAccessTypes())));
            groupAccessTypes.forEach((k, v) -> {
                if (AccessTypes.READ_ONLY_TYPES.contains(v) && !request.isReadOnly())
                    throwUserAccessDenied("user access denied", request.getData());
            });
        }

        return checkNext(request);
    }

    private void throwUserAccessDenied(String message, Map<String, Object> data) throws UserAccessDeniedException {
        throw new UserAccessDeniedException(message, (User)data.get("user"));
    }
}
