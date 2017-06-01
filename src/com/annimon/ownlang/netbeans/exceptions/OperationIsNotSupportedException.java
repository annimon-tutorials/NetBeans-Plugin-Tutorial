package com.annimon.ownlang.netbeans.exceptions;

public final class OperationIsNotSupportedException extends RuntimeException {

    public OperationIsNotSupportedException(Object operation) {
        super("Operation " + operation + " is not supported");
    }
    
    public OperationIsNotSupportedException(Object operation, String message) {
        super("Operation " + operation + " is not supported " + message);
    }
}
