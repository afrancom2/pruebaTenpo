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

public class PercentageServiceTest {

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
        // Arrange
        double externalPercentage = 10.0;
        PercentageDTO expectedDto = PercentageDTO.builder().percentage(externalPercentage).build();
        when(externalPercentageService.getExternalPercentage()).thenReturn(externalPercentage);
        when(cacheManager.getCache("percentage")).thenReturn(cache);

        // Act
        PercentageDTO result = percentageService.gettingPercentage();

        // Assert
        assertThat(result).isEqualTo(expectedDto);
        verify(cache).put("percentage", expectedDto);  // Verificamos que se haya almacenado en caché
    }

    @Test
    void gettingPercentage_shouldReturnPercentageFromCacheWhenExternalServiceFails() {
        // Arrange
        double cachedPercentage = 10.0;
        PercentageDTO cachedDto = PercentageDTO.builder().percentage(cachedPercentage).build();
        when(externalPercentageService.getExternalPercentage()).thenThrow(new PercentageException("External service failed"));
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("percentage", PercentageDTO.class)).thenReturn(cachedDto);

        // Act
        PercentageDTO result = percentageService.gettingPercentage();

        // Assert
        assertThat(result).isEqualTo(cachedDto);  // Verificamos que se haya retornado desde la caché
    }

    @Test
    void gettingPercentage_shouldThrowPercentageExceptionWhenBothServiceAndCacheFail() {
        // Arrange
        when(externalPercentageService.getExternalPercentage()).thenThrow(new PercentageException("External service failed"));
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("percentage", PercentageDTO.class)).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> percentageService.gettingPercentage())
                .isInstanceOf(PercentageException.class)
                .hasMessageContaining("NotFound percentage exception");
    }

    @Test
    void gettingPercentage_shouldReturnPercentageFromCacheWhenCacheIsNotNull() {
        // Arrange
        double cachedPercentage = 15.0;
        PercentageDTO cachedDto = PercentageDTO.builder().percentage(cachedPercentage).build();

        // Simulamos que el servicio externo no falla
        when(externalPercentageService.getExternalPercentage()).thenThrow(new PercentageException("External service failed"));

        // Configuramos el mock de cache para que devuelva un valor ya guardado
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("percentage", PercentageDTO.class)).thenReturn(cachedDto);

        // Act
        PercentageDTO result = percentageService.gettingPercentage();

        // Assert
        assertThat(result).isEqualTo(cachedDto);  // Verificamos que se haya retornado desde la caché
        verify(cache).get("percentage", PercentageDTO.class);  // Verificamos que se haya intentado obtener el valor de la caché
    }

    @Test
    void gettingPercentage_shouldStillReturnWhenCacheIsNull() {
        // Arrange
        double externalPercentage = 20.0;
        PercentageDTO expectedDto = PercentageDTO.builder().percentage(externalPercentage).build();

        when(externalPercentageService.getExternalPercentage()).thenReturn(externalPercentage);
        when(cacheManager.getCache("percentage")).thenReturn(null); // Simula que la caché no existe

        // Act
        PercentageDTO result = percentageService.gettingPercentage();

        // Assert
        assertThat(result).isEqualTo(expectedDto);
    }
}
