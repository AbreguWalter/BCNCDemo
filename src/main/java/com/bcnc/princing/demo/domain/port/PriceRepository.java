package com.bcnc.princing.demo.domain.port;

import java.time.LocalDateTime;
import java.util.Optional;

import com.bcnc.princing.demo.domain.model.Price;

public interface PriceRepository {
    Optional<Price> findValidPriceAtDate(LocalDateTime startOfDay, Long productId, Long brandId);
}
