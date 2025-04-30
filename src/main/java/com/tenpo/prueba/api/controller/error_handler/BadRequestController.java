package com.tenpo.prueba.api.controller.error_handler;

import com.tenpo.prueba.api.models.response.error.BaseErrorResponse;
import com.tenpo.prueba.api.models.response.error.ErrorResponse;
import com.tenpo.prueba.infraestructure.service.HistoryService;
import com.tenpo.prueba.util.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadRequestController {

    private final HistoryService historyService;

    @ExceptionHandler({ForbiddenException.class})
    public BaseErrorResponse handleForbidden(RuntimeException exception, HttpServletRequest request) {

        historyService.saveHistory(request.getRequestURI(), request.getQueryString(), exception.getMessage(), true);
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
