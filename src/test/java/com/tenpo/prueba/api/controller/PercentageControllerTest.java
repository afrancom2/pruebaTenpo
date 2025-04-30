package com.tenpo.prueba.api.controller;

import com.tenpo.prueba.api.models.request.SumRequest;
import com.tenpo.prueba.infraestructure.abstract_service.ICalculate;
import com.tenpo.prueba.infraestructure.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PercentageController.class)
class PercentageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ICalculate calculateService;

    @MockitoBean
    private HistoryService historyService;

    @Test
    void shouldReturnSumResult_whenValidRequest() throws Exception {
        // Arrange
        Double expectedResult = 33.0;
        Mockito.when(calculateService.calculateSum(Mockito.any(SumRequest.class)))
                .thenReturn(expectedResult);

        // Act & Assert
        mvc.perform(post("/percentage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "first": 10.0,
                                    "second": 20.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(expectedResult))
                .andExpect(jsonPath("$.status").value("200 OK"));
    }

    @Test
    void shouldReturnNotFound_whenServiceReturnsNull() throws Exception {
        // Arrange
        Mockito.when(calculateService.calculateSum(Mockito.any(SumRequest.class)))
                .thenReturn(null);

        // Act & Assert
        mvc.perform(post("/percentage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "first": 10.0,
                                    "second": 20.0
                                }
                                """))
                .andExpect(status().isNotFound());
    }
}