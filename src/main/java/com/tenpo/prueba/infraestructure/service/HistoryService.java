package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.domain.entity.History;
import com.tenpo.prueba.domain.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Async
    public void saveHistory(String endpoint, String requestParams, String response, boolean isError) {
        log.info("saveHistory INIT");
        History history = History.builder()
                .date(LocalDateTime.now())
                .endpoint(endpoint)
                .requestParams(requestParams)
                .response(response)
                .isError(isError)
                .build();
        log.info("saveHistory history: {}", history.toString());
        historyRepository.save(history);
    }
}
