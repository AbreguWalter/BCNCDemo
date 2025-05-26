package com.bcnc.princing.demo.application.serviceImpl;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplTest {

  @Mock
  private PriceRepository priceRepository;

  @InjectMocks
  private PriceServiceImpl priceService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void givenDateTimeProductAndBrand_whenPriceExists_thenReturnIt() {
    // Given
    LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0);
    Long productId = 35455L;
    String brand = "ZARA";

    Price expectedPrice = new Price(
        brand,
        dateTime.minusHours(1),
        dateTime.plusHours(1),
        "Afternoon promo",
        "Product 35455",
        1,
        new BigDecimal("25.45"),
        "EUR"
    );

    when(priceRepository.findValidPriceAtDate(dateTime, productId, 1L))
        .thenReturn(Optional.of(expectedPrice));

    // When
    Optional<Price> result = priceService.getApplicablePrice(dateTime, productId, 1L);

    // Then
    assertTrue(result.isPresent());
    assertEquals(expectedPrice, result.get());
  }

  @Test
  void givenDateTimeProductAndBrand_whenNoPrice_thenReturnEmpty() {
    // Given
    LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0);
    when(priceRepository.findValidPriceAtDate(dateTime, 35455L, 1L))
        .thenReturn(Optional.empty());

    // When
    Optional<Price> result = priceService.getApplicablePrice(dateTime, 35455L, 1L);

    // Then
    assertTrue(result.isEmpty());
  }
}
