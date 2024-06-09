package com.glos.api.operationservice;

import com.glos.api.operationservice.exception.ExecutionOperationException;
import com.glos.api.operationservice.exception.InvalidOperationDataPropertiesException;
import com.glos.api.operationservice.exception.OperationExpiredException;
import com.glos.api.operationservice.exception.OperationNotFoundException;
import com.glos.api.operationservice.util.VerificationCodeGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OperationService {

    private final VerificationCodeGenerator codeGenerator = new VerificationCodeGenerator();
    private final Set<Operation> operations;
    private final OperationExecutor executor;

    public OperationService() {
        this.operations = new HashSet<>();
        this.executor = new OperationExecutor();
    }

    public Operation create(String action, Map<String, String> data, int expired) {
        collectAllExpired();
        Action actionObj = Action.valueOfIgnoreCase(action);

        if (!actionObj.checkProperties(data, "code")) {
            throw new InvalidOperationDataPropertiesException("Invalid data properties");
        }

        String user = data.get("username");
        Optional<Operation> operationOpt = operations.stream()
                .filter(x -> x.getData().get("username").equals(user))
                .filter(x -> x.getAction().equalsIgnoreCase(action))
                .findFirst();
        operationOpt.ifPresent(operations::remove);

        final LocalDateTime now = LocalDateTime.now();
        Operation operation = new Operation();
        operation.setId(UUID.randomUUID());
        operation.setCode(codeGenerator.generateVerificationCode());
        operation.setAction(action);
        operation.setData(data);
        operation.setCreatedDatetime(now);
        operation.setExpiredDatetime(now.plusSeconds(expired));

        operations.add(operation);

        sendMessage(data.get("email"), operation);

        return operation;
    }

    private void sendMessage(String email, Operation operation) {
        // TODO: Send code to user email
    }

    public boolean execute(Operation operation) {
        return execute(operation.getCode());
    }

    public boolean execute(String code) {
        collectAllExpired();
        validateVerificationCode(code);
        Optional<Operation> operationOpt = operations.stream()
                .filter(x -> x.getCode().equals(code))
                .findFirst();

        Operation operation = operationOpt.orElseThrow(
                () -> new OperationNotFoundException("Operation not found", code)
        );

        if (operation.isExpired()) {
            final int expiredSeconds = getSecondsExpiredOperation(operation);
            throw new OperationExpiredException(String.format(
                    "Operation %s is expired %d seconds", code, expiredSeconds
            ), operation);
        }
        if (!executor.execute(operation)) {
            throw new ExecutionOperationException("Operation was not complate", code);
        }
        operation.setExpiredDatetime(LocalDateTime.now());
        return true;
    }

    private void validateVerificationCode(String code) {

    }

    private void collectAllExpired() {
        operations.removeIf(x -> {
            System.out.println(x.isExpired());
            return x.isExpired() &&
                    getSecondsExpiredOperation(x) > Operations.DEFAULT_EXPIRED_SECONDS;
        });
    }

    private int getSecondsExpiredOperation(Operation operation) {
        if (!operation.isExpired())
            return 0;
        final int nowSeconds = LocalDateTime.now().getSecond();
        final int expiredDateSecond = operation.getExpiredDatetime().getSecond();
        return nowSeconds - expiredDateSecond;
    }

    private void validateDataOperation(String action, Map<String, String> data) {
        try {
            Action actions = Action.valueOf(action);
            Set<String> properties = actions.getProperties();
            properties.forEach(x -> {
                if (!data.containsKey(x)) {
                    throw new RuntimeException(String.format("required property %s not found", x));
                }
            });
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("action %s not found", action));
        }
    }

}
