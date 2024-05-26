package com.aston_org.mainservice.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String invalidDataProvided) {
        super(invalidDataProvided);
    }
}
