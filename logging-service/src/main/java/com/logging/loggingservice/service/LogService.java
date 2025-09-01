package com.logging.loggingservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.logging.loggingservice.repository.LogRepository;
import com.logging.loggingservice.mapper.LogMapper;
import com.logging.loggingservice.dto.LogMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logging.loggingservice.entity.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogMapper logMapper;

    @KafkaListener(topics = "logging-topic", groupId = "logging-group")
    public void listen(String rawMessage) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LogMessage logMessage = mapper.readValue(rawMessage, LogMessage.class);
            Log log = logMapper.toLogEntity(logMessage);
            logRepository.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
