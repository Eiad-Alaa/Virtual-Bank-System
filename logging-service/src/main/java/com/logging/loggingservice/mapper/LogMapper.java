package com.logging.loggingservice.mapper;

import com.logging.loggingservice.dto.LogMessage;
import org.springframework.stereotype.Component;
import com.logging.loggingservice.entity.Log;

import java.time.LocalDateTime;

@Component
public class LogMapper {
    public Log toLogEntity(LogMessage logMessage) {
        return Log.builder()
                .message(logMessage.getMessage())
                .messageType(logMessage.getMessageType())
                .dateTime(LocalDateTime.parse(logMessage.getDateTime()))
                .producerService(logMessage.getProducerService())
                .build();
    }
}

