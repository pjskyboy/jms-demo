package com.freesundance.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class Router {

    private static List<String> allowedValues = Arrays.asList("left", "right");

    public String determineMessageTargetChannel(Message<String> message) {
        String payload = message.getPayload().toLowerCase();
        log.info("payload={}", payload);
        String result = allowedValues.stream().filter(payload::contains).findAny().orElse(null);
        log.info("result={}", result);
        return result;
    }
}
