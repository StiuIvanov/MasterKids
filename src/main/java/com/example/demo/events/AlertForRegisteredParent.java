package com.example.demo.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AlertForRegisteredParent {

    private Logger LOGGER = LoggerFactory.getLogger(AlertForRegisteredParent.class);

    @EventListener(ParentRegisteredEvent.class)
    public void onOrderCreated(ParentRegisteredEvent parentRegisteredEvent) {
        LOGGER.info("Parent was registered - Username: {}, Full name: {} {} at {}",
                parentRegisteredEvent.getUsername(),
                parentRegisteredEvent.getFirstName(),
                parentRegisteredEvent.getLastName(),
                LocalDateTime.now());
    }

}
