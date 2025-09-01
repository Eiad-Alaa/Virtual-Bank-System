package com.bff.bffservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class LogProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendLog(String message, String messageType) {
        String producerService = "bff-service";
        Map<String, String> logMessage = Map.of(
                "message", message,
                "messageType", messageType,
                "dateTime", LocalDateTime.now().toString(),
                "producerService", producerService
        );
        try {
            ObjectMapper mapper = new ObjectMapper();
            String logJson = mapper.writeValueAsString(logMessage);
            kafkaTemplate.send("logging-topic", logJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}