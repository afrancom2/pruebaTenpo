package com.tenpo.prueba.api.controller;

import com.tenpo.prueba.infraestructure.dto.ExternalPercentageConfig;
import com.tenpo.prueba.infraestructure.service.ExternalPercentageService;
import com.tenpo.prueba.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/mock/external-percentage")
@RequiredArgsConstructor
@Tag(name = "External Percentage Mock Controller")
public class ExternalPercentageMockController {

    private final ExternalPercentageService externalPercentageService;
    private final CacheManager cacheManager;

    @Operation(summary = "Change values percentage service external")
    @PostMapping("/config")
    public ResponseEntity<String> updateMockConfig(@RequestBody ExternalPercentageConfig config) {
        externalPercentageService.updateConfig(config.isShouldFail(), config.getFixedPercentage());
        if (cacheManager.getCache(Constants.PERCENTAGE_CACHE) != null) {
            Objects.requireNonNull(cacheManager.getCache(Constants.PERCENTAGE_CACHE)).clear();
        }
        return ResponseEntity.ok("Mock config updated");
    }
}
