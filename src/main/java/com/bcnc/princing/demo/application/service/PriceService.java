package com.bcnc.princing.demo.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.bcnc.princing.demo.domain.model.Price;

public interface PriceService {

    Optional<Price> getApplicablePrice(LocalDateTime date, Long productId, Long brandId);
}
