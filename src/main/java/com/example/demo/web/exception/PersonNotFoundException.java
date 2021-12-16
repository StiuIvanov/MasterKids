package com.example.demo.web.exception;

public class PersonNotFoundException extends RuntimeException{

    private final Long objectId;

    public PersonNotFoundException(Long objectId) {
        super("Object with id " + objectId + " not found!");
        this.objectId = objectId;
    }

    public Long getPersonId() {
        return objectId;
    }
}
