package com.bcnc.princing.demo.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.bcnc.princing.demo.application.service.PriceService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Override
    public Optional<Price> getApplicablePrice(LocalDateTime inputDateTime, Long productId, Long brandId) {
        final String correlationId = MDC.get("correlationId");
        log.info("[{}] Looking up price for productId={}, brandId={}", correlationId, productId, brandId);
        return priceRepository.findValidPriceAtDate(inputDateTime, productId, brandId);
    }
}

