package com.ailaaj.paf.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaOrderProducer {
    String CHANNELNAME = "binding-out-order";

    @Output(CHANNELNAME)
    MessageChannel output();
}
