package com.tenpo.prueba.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.prueba.infraestructure.dto.ExternalPercentageConfig;
import com.tenpo.prueba.infraestructure.service.ExternalPercentageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalPercentageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ExternalPercentageService externalPercentageService;

    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdateMockConfigSuccessfully() throws Exception {
        var config = new ExternalPercentageConfig(true, 15.5);
        var json = objectMapper.writeValueAsString(config);

        var mockCache = Mockito.mock(org.springframework.cache.Cache.class);

        mockCache.clear();
        Mockito.when(cacheManager.getCache("percentage-cache")).thenReturn(mockCache);

        mvc.perform(post("/mock/external-percentage/config")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Mock config updated"));

        Mockito.verify(externalPercentageService).updateConfig(true, 15.5);
        Mockito.verify(mockCache).clear();
    }

    @Test
    void shouldUpdateMockConfigSuccessfullys() throws Exception {
        var config = new ExternalPercentageConfig(true, 15.5);
        var json = objectMapper.writeValueAsString(config);

        var mockCache = Mockito.mock(org.springframework.cache.Cache.class);

        Mockito.when(cacheManager.getCache("percentage")).thenReturn(mockCache);

        mvc.perform(post("/mock/external-percentage/config")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Mock config updated"));

        Mockito.verify(externalPercentageService).updateConfig(true, 15.5);
    }
}
