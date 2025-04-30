package com.tenpo.prueba.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SumRequest implements Serializable {
    @NotNull(message = "First number is required")
    @Positive
    @JsonProperty("first")
    @Schema(description = "First number operation sum")
    private Double first;

    @NotNull(message = "Second number is required")
    @Positive
    @JsonProperty("second")
    @Schema(description = "Second number operation sum")
    private Double second;
}
