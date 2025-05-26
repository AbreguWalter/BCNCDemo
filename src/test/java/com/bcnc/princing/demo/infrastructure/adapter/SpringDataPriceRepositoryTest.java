package com.bcnc.princing.demo.infrastructure.adapter;

import com.bcnc.princing.demo.infrastructure.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpringDataPriceRepositoryTest {

  @Autowired
  private SpringDataPriceRepository repository;

  @Test
  void givenValidInput_whenPricesMatch_thenReturnsSortedByPriority() {
    // Given
    LocalDateTime queryDate = LocalDateTime.of(2020, 6, 14, 16, 0); // Fecha en rango de dos tarifas

    // When
    List<PriceEntity> results = repository.findApplicablePrice(queryDate, 35455L, 1L);

    // Then
    assertThat(results).isNotEmpty();
    assertThat(results.get(0).getPriority()).isEqualTo(1); // El más prioritario va primero
  }

  @Test
  void givenInvalidDate_whenNoPriceMatches_thenReturnsEmptyList() {
    // Given
    LocalDateTime queryDate = LocalDateTime.of(2026, 1, 1, 0, 0);

    // When
    List<PriceEntity> results = repository.findApplicablePrice(queryDate, 35455L, 1L);

    // Then
    assertThat(results).isEmpty();
  }
}