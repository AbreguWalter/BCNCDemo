package com.bcnc.princing.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void isValidForShouldReturnTrueWhenDateWithinRange() {
        final LocalDateTime from = LocalDateTime.of(2020, 6, 14, 0, 0);
        final LocalDateTime to = LocalDateTime.of(2020, 6, 14, 23, 59);
        final Price price = new Price("ZARA", from, to, 1, 1L, 1, BigDecimal.TEN, "EUR");

        assertThat(price.isValidFor(LocalDateTime.of(2020, 6, 14, 12, 0))).isTrue();
    }

    @Test
    void isValidForShouldReturnFalseWhenDateOutOfRange() {
        final LocalDateTime from = LocalDateTime.of(2020, 6, 14, 0, 0);
        final LocalDateTime to = LocalDateTime.of(2020, 6, 14, 23, 59);
        final Price price = new Price("ZARA", from, to, 1, 1L, 1, BigDecimal.TEN, "EUR");

        assertThat(price.isValidFor(LocalDateTime.of(2020, 6, 15, 0, 0))).isFalse();
    }

    @Test
    void hasHigherPriorityThanShouldComparePriorities() {
        final Price a = new Price("ZARA", null, null, 1, 1L, 2, BigDecimal.ONE, "EUR");
        final Price b = new Price("ZARA", null, null, 1, 1L, 1, BigDecimal.ONE, "EUR");
        assertThat(a.hasHigherPriorityThan(b)).isTrue();
        assertThat(b.hasHigherPriorityThan(a)).isFalse();
    }
}
