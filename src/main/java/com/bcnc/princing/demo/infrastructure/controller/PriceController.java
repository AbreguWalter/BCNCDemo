package com.bcnc.princing.demo.infrastructure.controller;

import java.time.LocalDateTime;

import jakarta.validation.Valid;

import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcnc.princing.demo.application.service.PriceService;
import com.bcnc.princing.demo.config.NotFoundException;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.controller.dto.PriceResponse;
import com.bcnc.princing.demo.infrastructure.controller.mapper.PriceResponseMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Slf4j
public class PriceController {

    private final PriceService priceService;

    private final PriceResponseMapper priceResponseMapper;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestHeader(name = "X-Request-ID", required = true) String requestId,
            @RequestHeader(name = "X-Correlation-ID", required = true) String correlationId,
            @Valid @RequestParam LocalDateTime date,
            @Valid @RequestParam Long productId,
            @Valid @RequestParam Long brandId
    ) {
        MDC.put("correlationId", correlationId);
        MDC.put("requestId", requestId);
        log.info("Consulta de precio para producto {}, marca {} en {}",
                    productId, brandId, date);

        final Price price = priceService.getApplicablePrice(date, productId, brandId)
                .orElseThrow(() -> new NotFoundException("No applicable price found."));

        MDC.clear();

        return ResponseEntity.ok(priceResponseMapper.toResponse(price));
    }
}
