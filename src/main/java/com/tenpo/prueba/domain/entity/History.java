package com.tenpo.prueba.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String endpoint;

    @Schema(description = "Request params endpoint")
    private String requestParams;

    @Schema(description = "Response with error or value percentage")
    private String response;

    @Schema(description = "Status whether the endpoint failed or not")
    private boolean isError;
}
