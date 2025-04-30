package com.tenpo.prueba.infraestructure.abstract_service;

import com.tenpo.prueba.api.models.request.SumRequest;

public interface ICalculate {
    Double calculateSum (SumRequest sumRequest);
}
