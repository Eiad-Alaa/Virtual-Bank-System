package com.logging.loggingservice.dto;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {
    private String message;
    private String dateTime;
    private String messageType;
    private String producerService;
}
