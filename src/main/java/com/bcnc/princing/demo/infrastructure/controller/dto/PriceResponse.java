package com.bcnc.princing.demo.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        String product,
        String brand,
        String priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) { }
