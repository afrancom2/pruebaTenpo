package com.tenpo.prueba.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PercentageDTO implements Serializable {
    private Double percentage;
}
