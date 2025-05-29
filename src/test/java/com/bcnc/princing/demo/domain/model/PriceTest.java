package com.bcnc.princing.demo.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

  @Test
  void isValidFor_shouldReturnTrueWhenDateWithinRange() {
    LocalDateTime from = LocalDateTime.of(2020, 6, 14, 0, 0);
    LocalDateTime to = LocalDateTime.of(2020, 6, 14, 23, 59);
    Price price = new Price("ZARA", from, to, 1, 1L, 1, BigDecimal.TEN, "EUR");

    assertThat(price.isValidFor(LocalDateTime.of(2020, 6, 14, 12, 0))).isTrue();
  }

  @Test
  void isValidFor_shouldReturnFalseWhenDateOutOfRange() {
    LocalDateTime from = LocalDateTime.of(2020, 6, 14, 0, 0);
    LocalDateTime to = LocalDateTime.of(2020, 6, 14, 23, 59);
    Price price = new Price("ZARA", from, to, 1, 1L, 1, BigDecimal.TEN, "EUR");

    assertThat(price.isValidFor(LocalDateTime.of(2020, 6, 15, 0, 0))).isFalse();
  }

  @Test
  void hasHigherPriorityThan_shouldComparePriorities() {
    Price a = new Price("ZARA", null, null, 1, 1L, 2, BigDecimal.ONE, "EUR");
    Price b = new Price("ZARA", null, null, 1, 1L, 1, BigDecimal.ONE, "EUR");
    assertThat(a.hasHigherPriorityThan(b)).isTrue();
    assertThat(b.hasHigherPriorityThan(a)).isFalse();
  }
}