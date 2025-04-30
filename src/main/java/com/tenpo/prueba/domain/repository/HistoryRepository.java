package com.tenpo.prueba.domain.repository;

import com.tenpo.prueba.domain.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
