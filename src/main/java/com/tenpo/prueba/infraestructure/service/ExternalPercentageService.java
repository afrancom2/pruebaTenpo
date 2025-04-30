package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.util.exception.PercentageException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Service
public class ExternalPercentageService {

    @Value("${app.data.variables.shouldFail}")
    private boolean shouldFail;
    @Value("${app.data.variables.fixedPercentage}")
    private double fixedPercentage;

    public double getExternalPercentage() {
        if (shouldFail) {
            throw new PercentageException("External service percentage failed");
        }
        return fixedPercentage;
    }

    public void updateConfig(boolean shouldFail, double fixedPercentage) {
        this.shouldFail = shouldFail;
        this.fixedPercentage = fixedPercentage;
    }

}

