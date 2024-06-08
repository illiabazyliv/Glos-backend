package com.glos.api.operationservice;

import java.util.HashMap;
import java.util.Map;

public class OperationAdapterRegister {
    private static final Map<String, OperationAdapter> adapters = new HashMap<>();

    static {
        adapters.put(Action.CONFIRM_EMAIL.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for confirm email
        } );
        adapters.put(Action.CONFIRM_PHONE_NUMBER.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for confirm phone number
        } );
        adapters.put(Action.CHANGE_USERNAME.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for change username
        } );
        adapters.put(Action.CHANGE_PASSWORD.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for change password
        } );
        adapters.put(Action.CHANGE_EMAIL.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for change email
        } );
        adapters.put(Action.CHANGE_PHONE_NUMBER.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for change phone number
        } );
        adapters.put(Action.DROP_ACCOUNT.name(), () -> (x) -> {
            final Map<String, String> data = x.getData();
            // TODO: Send request to user-service for drop account
        } );
    }


    public static void appendAdapter(String action, OperationAdapter adapter) {
        adapters.put(action, adapter);
    }

    public static OperationAdapter getAdapter(String action) {
        Action actionObj = Action.valueOfIgnoreCase(action);
        return adapters.get(actionObj.name());
    }
}
