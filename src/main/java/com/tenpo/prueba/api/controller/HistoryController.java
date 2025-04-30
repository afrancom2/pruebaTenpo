package com.tenpo.prueba.api.controller;

import com.tenpo.prueba.domain.entity.History;
import com.tenpo.prueba.domain.repository.HistoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "History Controller")
public class HistoryController {

    private final HistoryRepository historyRepository;

    @Operation(summary = "Get history with pagination")
    @GetMapping("/history")
    public ResponseEntity<Page<History>> getHistory(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("date").descending());
        return ResponseEntity.ok(historyRepository.findAll(pageRequest));
    }
}
