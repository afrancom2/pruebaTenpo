package com.tenpo.prueba.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SumResponse {
    @Schema(description = "Status operation sum")
    private String status;
    @Schema(description = "Result with percentage apply")
    private Double result;
}
