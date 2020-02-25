package com.derzhavets.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqListener {
    @StreamListener(Sink.INPUT)
    public void handleMessage(String message) {
        log.info(String.format("New incoming message: [%s]", message));
    }
}
