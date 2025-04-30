package com.tenpo.prueba.api.controller;

import com.tenpo.prueba.api.models.request.SumRequest;
import com.tenpo.prueba.api.models.response.SumResponse;
import com.tenpo.prueba.infraestructure.abstract_service.ICalculate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/percentage")
@AllArgsConstructor
@Slf4j
@Tag(name = "Percentage Controller")
public class PercentageController {

    private final ICalculate icalculate;

    @Operation(summary = "Sum values with percentage")
    @PostMapping
    public ResponseEntity<SumResponse> sumValue(@RequestBody SumRequest sumRequest) {
        Double percentageValue = icalculate.calculateSum(sumRequest);
        return percentageValue != null ? ResponseEntity.ok(
                SumResponse.builder()
                        .result(percentageValue)
                        .status(HttpStatus.OK.toString())
                        .build()) : ResponseEntity.notFound().build();
    }
}
