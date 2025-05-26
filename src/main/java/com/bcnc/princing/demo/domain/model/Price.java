package com.bcnc.princing.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        String brand,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String priceList,
        String product,
        Integer priority,
        BigDecimal price,
        String currency
) { }
