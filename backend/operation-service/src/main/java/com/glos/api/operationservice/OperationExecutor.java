package com.glos.api.operationservice;

import com.glos.api.operationservice.exception.ExecutionOperationException;

public class OperationExecutor {

    public boolean execute(Operation operation) {
        try {
            OperationAdapter adapter = OperationAdapterRegister
                    .getAdapter(operation.getAction());
            adapter.getConsumer().accept(operation);
        } catch (Exception e) {
            throw new ExecutionOperationException();
        }
        return true;
    }

}
