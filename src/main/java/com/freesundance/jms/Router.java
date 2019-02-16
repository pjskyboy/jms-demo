package com.freesundance.jms;

import org.omg.SendingContext.RunTime;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Router {

    private static List<String> allowedValues = Arrays.asList(new String[]{
            "left", "right"
    });

    public String determineMessageTargetChannel(Message<String> message) {
        String payload = message.getPayload().toLowerCase();
        return allowedValues.stream().filter(t -> payload.contains(t)).findAny().orElse(null);
    }
}
