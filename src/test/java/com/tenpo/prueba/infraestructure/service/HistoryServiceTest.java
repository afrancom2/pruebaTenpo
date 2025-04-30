package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.domain.entity.History;
import com.tenpo.prueba.domain.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @MockitoBean
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryService historyService;


    @BeforeEach
    void setUp() {
        historyService = new HistoryService(historyRepository); // instancia real del servicio
    }

    @Test
    void saveHistory_shouldCallHistoryRepositorySave() throws Exception {
        // Arrange
        String endpoint = "/some-endpoint";
        String requestParams = "param1=value1";
        String response = "response";
        boolean isError = false;

        // Act
        historyService.saveHistory(endpoint, requestParams, response, isError);

        // Esperamos para que el hilo @Async termine
        Thread.sleep(500); // Ajusta si es necesario

        // Assert
        verify(historyRepository, times(1)).save(any(History.class));
    }
}
