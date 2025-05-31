package com.bcnc.princing.demo.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        Long product,
        String brand,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) { }
