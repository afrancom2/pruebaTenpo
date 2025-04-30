package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.util.exception.PercentageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ExternalPercentageServiceTest {
    @InjectMocks
    private ExternalPercentageService externalPercentageService;

    @Mock
    private Environment environment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExternalPercentage_shouldReturnFixedPercentageWhenNoFailure() {
        when(environment.getProperty("app.data.variables.shouldFail")).thenReturn("false");
        when(environment.getProperty("app.data.variables.fixedPercentage")).thenReturn("20.5");

        externalPercentageService.updateConfig(false, 20.5);

        double result = externalPercentageService.getExternalPercentage();

        assertThat(result).isEqualTo(20.5);
    }

    @Test
    void getExternalPercentage_shouldThrowPercentageExceptionWhenFailureOccurs() {
        when(environment.getProperty("app.data.variables.shouldFail")).thenReturn("true");
        when(environment.getProperty("app.data.variables.fixedPercentage")).thenReturn("20.5");

        externalPercentageService.updateConfig(true, 20.5);

        assertThatThrownBy(() -> externalPercentageService.getExternalPercentage())
                .isInstanceOf(PercentageException.class)
                .hasMessageContaining("NotFound percentage exception");

    }
}
