package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.domain.entity.History;
import com.tenpo.prueba.domain.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @MockitoBean
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryService historyService;


    @BeforeEach
    void setUp() {
        historyService = new HistoryService(historyRepository);
    }

    @Test
    void saveHistory_shouldCallHistoryRepositorySave() throws Exception {
        String endpoint = "/percentage";
        String requestParams = "param=122";
        String response = "response";
        boolean isError = false;

        historyService.saveHistory(endpoint, requestParams, response, isError);

        Thread.sleep(500);

        verify(historyRepository, times(1)).save(any(History.class));
    }
}
