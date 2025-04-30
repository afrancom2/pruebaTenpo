package com.tenpo.prueba.api.controller.error_handler;

import com.tenpo.prueba.api.models.response.error.BaseErrorResponse;
import com.tenpo.prueba.infraestructure.service.HistoryService;
import com.tenpo.prueba.util.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BadRequestControllerTest {

    private HistoryService historyService;
    private BadRequestController badRequestController;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        historyService = mock(HistoryService.class);
        badRequestController = new BadRequestController(historyService);
        request = mock(HttpServletRequest.class);
    }

    @Test
    void shouldHandleForbiddenException_andSaveHistory() {
        // Arrange
        String uri = "/percentage";
        String queryString = "first=1&second=2";
        String errorMessage = "Forbidden request";

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getQueryString()).thenReturn(queryString);

        ForbiddenException exception = new ForbiddenException();

        // Act
        BaseErrorResponse response = badRequestController.handleForbidden(exception, request);

        // Assert
        //assertThat(response.getMessage()).isEqualTo(errorMessage);
        assertThat(response.getStatus()).isEqualTo("FORBIDDEN");
        assertThat(response.getCode()).isEqualTo(403);

        // Verifica que se haya llamado a saveHistory con los valores esperados
        verify(historyService, times(1))
                .saveHistory(uri, queryString, errorMessage, true);
    }
}
