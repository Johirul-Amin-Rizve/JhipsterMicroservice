package com.ailaaj.paf.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaOrderConsumer {
    String CHANNELNAME = "binding-in-order";

    @Input(CHANNELNAME)
    MessageChannel input();
}
