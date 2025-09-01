package com.bff.bffservice.controllers;

import com.bff.bffservice.dto.DashboardResp;
import com.bff.bffservice.services.DashboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bff")
public class BFFController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard/{userId}")
    public Mono<ResponseEntity<DashboardResp>> getDashboard(@PathVariable UUID userId){

        return dashboardService.getDashboard(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
