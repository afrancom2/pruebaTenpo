package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.api.models.request.SumRequest;
import com.tenpo.prueba.infraestructure.dto.PercentageDTO;
import com.tenpo.prueba.util.Constants;
import com.tenpo.prueba.util.exception.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CalculateServiceTest {
    private PercentageService percentageService;
    private HistoryService historyService;
    private CalculateService calculateService;

    @BeforeEach
    void setUp() {
        percentageService = mock(PercentageService.class);
        historyService = mock(HistoryService.class);
        calculateService = new CalculateService(percentageService, historyService);
    }

    @Test
    void calculateSum_shouldReturnCorrectValueAndSaveHistory() {
        PercentageDTO mockPercentage = PercentageDTO.builder().percentage(10.0).build();
        SumRequest sumRequest = new SumRequest(10.0, 5.0);
        when(percentageService.gettingPercentage()).thenReturn(mockPercentage);

        double result = calculateService.calculateSum(sumRequest);

        assertThat(result).isEqualTo(16.5);

        ArgumentCaptor<String> responseCaptor = ArgumentCaptor.forClass(String.class);
        verify(historyService).saveHistory(
                eq(Constants.RESOURCE_SUM),
                eq(sumRequest.toString()),
                responseCaptor.capture(),
                eq(false)
        );

        assertThat(responseCaptor.getValue()).contains("result=16.5");
    }

    @Test
    void calculateSum_shouldThrowExceptionWhenRequestIsInvalid() {
        assertThrows(ForbiddenException.class, () -> calculateService.calculateSum(null));

        SumRequest badRequest1 = new SumRequest(null, 5.0);
        assertThrows(ForbiddenException.class, () -> calculateService.calculateSum(badRequest1));

        SumRequest badRequest2 = new SumRequest(10.0, null);
        assertThrows(ForbiddenException.class, () -> calculateService.calculateSum(badRequest2));
    }
}
