package com.example.demo.events;

import org.springframework.context.ApplicationEvent;

public class ParentRegisteredEvent extends ApplicationEvent {

    private final String username;
    private final String firstName;
    private final String lastName;

    public ParentRegisteredEvent(Object source, String username, String firstName, String lastName) {
        super(source);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
