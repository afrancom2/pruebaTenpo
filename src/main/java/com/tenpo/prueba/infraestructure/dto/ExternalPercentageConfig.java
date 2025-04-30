package com.tenpo.prueba.infraestructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExternalPercentageConfig {
    @Schema(description = "Value for failed external service")
    private boolean shouldFail;
    @Schema(description = "Percentage applicable for operation sum")
    private double fixedPercentage;
}
