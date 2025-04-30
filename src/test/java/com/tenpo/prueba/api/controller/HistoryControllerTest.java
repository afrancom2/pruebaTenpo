package com.tenpo.prueba.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.prueba.domain.entity.History;
import com.tenpo.prueba.domain.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HistoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private HistoryRepository historyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnPagedHistory() throws Exception {
        History h1 = History.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .endpoint("/test")
                .requestParams("a=1")
                .response("ok")
                .isError(false)
                .build();

        List<History> content = List.of(h1);
        Page<History> page = new PageImpl<>(content, PageRequest.of(0, 10), 1);

        Mockito.when(historyRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        mvc.perform(get("/history")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(h1.getId()))
                .andExpect(jsonPath("$.content[0].endpoint").value(h1.getEndpoint()))
                .andExpect(jsonPath("$.content[0].response").value(h1.getResponse()));
    }
}
