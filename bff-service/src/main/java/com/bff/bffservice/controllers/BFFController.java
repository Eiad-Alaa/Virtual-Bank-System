package com.bff.bffservice.controllers;

import com.bff.bffservice.dto.DashboardResp;
import com.bff.bffservice.services.DashboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bff.bffservice.producer.LogProducer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bff")
public class BFFController {

    @Autowired
    private final DashboardService dashboardService;

    @Autowired
    private LogProducer logProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/dashboard/{userId}")
    public Mono<ResponseEntity<DashboardResp>> getDashboard(@PathVariable UUID userId){
        logAsJson(userId, "Request");
        Mono<ResponseEntity<DashboardResp>> resp = dashboardService.getDashboard(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
        logAsJson(resp, "Response");
        return resp;
    }

    private void logAsJson(Object obj, String type) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            logProducer.sendLog(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
