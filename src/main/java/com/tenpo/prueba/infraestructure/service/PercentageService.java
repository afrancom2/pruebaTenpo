package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.infraestructure.abstract_service.IPercentageService;
import com.tenpo.prueba.infraestructure.dto.PercentageDTO;
import com.tenpo.prueba.util.exception.PercentageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PercentageService implements IPercentageService {

    private final ExternalPercentageService externalPercentageService;
    private final CacheManager cacheManager; // <-- agregamos CacheManager manual

    private static final String CACHE_NAME = "percentage";

    @Override
    public PercentageDTO gettingPercentage() {
        try {
            log.info("Getting percentage INIT");
            log.info("Getting percentage Fetching percentage from external service...");
            double externalPercentage = externalPercentageService.getExternalPercentage();
            PercentageDTO dto = PercentageDTO.builder()
                    .percentage(externalPercentage)
                    .build();

            // Guardar en caché manualmente
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                cache.put(CACHE_NAME, dto);
            }

            log.info("Getting percentage cache: {}", cache);

            log.info("Getting percentage FIN");
            return dto;
        } catch (Exception ex) {
            log.error("Getting percentage External service failed, trying cache...", ex);
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                PercentageDTO cachedPercentage = cache.get(CACHE_NAME, PercentageDTO.class);
                if (cachedPercentage != null) {
                    log.info("Getting percentage Found cached percentage: {}", cachedPercentage.getPercentage());
                    return cachedPercentage;
                }
            }
            throw new PercentageException("Getting percentage external service and cache failed.");
        }
    }
}
