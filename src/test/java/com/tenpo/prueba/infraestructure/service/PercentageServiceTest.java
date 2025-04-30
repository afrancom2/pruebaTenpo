package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.infraestructure.dto.PercentageDTO;
import com.tenpo.prueba.util.exception.PercentageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PercentageServiceTest {

    @Mock
    private ExternalPercentageService externalPercentageService;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    private PercentageService percentageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        percentageService = new PercentageService(externalPercentageService, cacheManager);
    }

    @Test
    void gettingPercentage_shouldReturnPercentageFromExternalService() {
        double externalPercentage = 10.0;
        PercentageDTO expectedDto = PercentageDTO.builder().percentage(externalPercentage).build();
        when(externalPercentageService.getExternalPercentage()).thenReturn(externalPercentage);
        when(cacheManager.getCache("percentage")).thenReturn(cache);

        PercentageDTO result = percentageService.gettingPercentage();

        assertThat(result).isEqualTo(expectedDto);
        verify(cache).put("percentage", expectedDto);
    }

    @Test
    void gettingPercentage_shouldReturnPercentageFromCacheWhenExternalServiceFails() {
        double cachedPercentage = 10.0;
        PercentageDTO cachedDto = PercentageDTO.builder().percentage(cachedPercentage).build();
        when(externalPercentageService.getExternalPercentage()).thenThrow(new PercentageException("External service failed"));
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("percentage", PercentageDTO.class)).thenReturn(cachedDto);

        PercentageDTO result = percentageService.gettingPercentage();

        assertThat(result).isEqualTo(cachedDto);
    }

    @Test
    void gettingPercentage_shouldThrowPercentageExceptionWhenBothServiceAndCacheFail() {
        when(externalPercentageService.getExternalPercentage()).thenThrow(new PercentageException("External service failed"));
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("percentage", PercentageDTO.class)).thenReturn(null);

        assertThatThrownBy(() -> percentageService.gettingPercentage())
                .isInstanceOf(PercentageException.class)
                .hasMessageContaining("NotFound percentage exception");
    }

    @Test
    void gettingPercentage_shouldReturnPercentageFromCacheWhenCacheIsNotNull() {
        double cachedPercentage = 15.0;
        PercentageDTO cachedDto = PercentageDTO.builder().percentage(cachedPercentage).build();

        when(externalPercentageService.getExternalPercentage()).thenThrow(new PercentageException("External service failed"));

        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("percentage", PercentageDTO.class)).thenReturn(cachedDto);

        PercentageDTO result = percentageService.gettingPercentage();

        assertThat(result).isEqualTo(cachedDto);
        verify(cache).get("percentage", PercentageDTO.class);
    }

    @Test
    void gettingPercentage_shouldStillReturnWhenCacheIsNull() {
        double externalPercentage = 20.0;
        PercentageDTO expectedDto = PercentageDTO.builder().percentage(externalPercentage).build();

        when(externalPercentageService.getExternalPercentage()).thenReturn(externalPercentage);
        when(cacheManager.getCache("percentage")).thenReturn(null);

        PercentageDTO result = percentageService.gettingPercentage();

        assertThat(result).isEqualTo(expectedDto);
    }
}
