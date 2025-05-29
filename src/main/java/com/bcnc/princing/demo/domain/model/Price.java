package com.bcnc.princing.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        String brand,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceListId,
        Long productId,
        Integer priority,
        BigDecimal price,
        String currency
) {

    // Lógica de dominio: ¿aplica esta tarifa en la fecha?
    public boolean isValidFor(LocalDateTime date) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate));
    }

    // ¿Tiene prioridad sobre otra tarifa?
    public boolean hasHigherPriorityThan(Price other) {
        if (other == null) {
            return true;
        }
        if (this.priority.equals(other.priority)) {
            // Nueva regla: si igual prioridad, el más reciente
            return this.startDate.isAfter(other.startDate);
        }
        return this.priority > other.priority;
    }

}
