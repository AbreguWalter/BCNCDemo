package com.bcnc.princing.demo.infrastructure.adapter;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRepositoryAdapterTest {

  @Mock
  private SpringDataPriceRepository jpaRepo;

  @InjectMocks
  private PriceRepositoryAdapter adapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void givenValidInput_whenPriceFound_thenMapToDomainCorrectly() {
    // Given
    LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0);

    BrandEntity brand = BrandEntity.builder()
        .id(1L)
        .name("ZARA")
        .build();

    ProductEntity product = ProductEntity.builder()
        .id(35455L)
        .name("Product 35455")
        .build();

    PriceListEntity priceList = PriceListEntity.builder()
        .id(2)
        .name("Afternoon promo")
        .build();

    CurrencyEntity currency = CurrencyEntity.builder()
        .id(1L)
        .symbol("EUR")
        .build();

    PriceEntity priceEntity = PriceEntity.builder()
        .brand(brand)
        .product(product)
        .priceList(priceList)
        .currency(currency)
        .startDate(dateTime.minusHours(1))
        .endDate(dateTime.plusHours(2))
        .priority(1)
        .price(BigDecimal.valueOf(25.45))
        .build();

    when(jpaRepo.findApplicablePrice(dateTime, 35455L, 1L)).thenReturn(List.of(priceEntity));

    // When
    Optional<Price> result = adapter.findValidPriceAtDate(dateTime, 35455L, 1L);

    // Then
    assertTrue(result.isPresent());

    Price price = result.get();
    assertEquals("ZARA", price.brand());
    assertEquals("Product 35455", price.product());
    assertEquals("Afternoon promo", price.priceList());
    assertEquals("EUR", price.currency());
    assertEquals(1, price.priority());
    assertEquals(BigDecimal.valueOf(25.45), price.price());
  }

  @Test
  void givenNoResults_whenCalled_thenReturnEmptyOptional() {
    // Given
    LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0);
    when(jpaRepo.findApplicablePrice(dateTime, 35455L, 1L)).thenReturn(List.of());

    // When
    Optional<Price> result = adapter.findValidPriceAtDate(dateTime, 35455L, 1L);

    // Then
    assertTrue(result.isEmpty());
  }
}
