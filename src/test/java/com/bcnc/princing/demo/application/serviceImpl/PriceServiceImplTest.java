package com.bcnc.princing.demo.application.serviceImpl;

import com.bcnc.princing.demo.application.service.BrandService;
import com.bcnc.princing.demo.application.service.CurrencyService;
import com.bcnc.princing.demo.application.service.PriceListService;
import com.bcnc.princing.demo.application.service.ProductService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PriceServiceImplTest {

  @Test
  void givenMultiplePrices_whenGetApplicablePrice_thenReturnsWithHighestPriority() {

    PriceRepository repo = Mockito.mock(PriceRepository.class);
    BrandService repo1 = Mockito.mock(BrandService.class);
    ProductService repo2 = Mockito.mock(ProductService.class);
    PriceListService repo3 = Mockito.mock(PriceListService.class);
    CurrencyService repo4 = Mockito.mock(CurrencyService.class);

    PriceServiceImpl service = new PriceServiceImpl(repo,repo1,repo2,repo3,repo4);

    LocalDateTime searchDate = LocalDateTime.of(2020, 6, 14, 16, 0);
    Price lowPriority = new Price("ZARA", searchDate.minusHours(2), searchDate.plusHours(2), 1, 1L, 0, new BigDecimal("20.00"), "EUR");
    Price highPriority = new Price("ZARA", searchDate.minusHours(1), searchDate.plusHours(1), 1, 1L, 2, new BigDecimal("25.45"), "EUR");

    Mockito.when(repo.findByProductIdAndBrandId(35455L, 1L))
        .thenReturn(List.of(lowPriority, highPriority));

    Optional<Price> result = service.getApplicablePrice(searchDate, 35455L, 1L);

    assertThat(result).isPresent();
    assertThat(result.get().priority()).isEqualTo(2);
    assertThat(result.get().price()).isEqualTo(new BigDecimal("25.45"));
  }

  @Test
  void whenNoPriceApplies_thenReturnsEmpty() {
    PriceRepository repo = Mockito.mock(PriceRepository.class);
    BrandService repo1 = Mockito.mock(BrandService.class);
    ProductService repo2 = Mockito.mock(ProductService.class);
    PriceListService repo3 = Mockito.mock(PriceListService.class);
    CurrencyService repo4 = Mockito.mock(CurrencyService.class);

    PriceServiceImpl service = new PriceServiceImpl(repo,repo1,repo2,repo3,repo4);

    Mockito.when(repo.findByProductIdAndBrandId(35455L, 1L)).thenReturn(List.of());

    Optional<Price> result = service.getApplicablePrice(LocalDateTime.now(), 35455L, 1L);

    assertThat(result).isEmpty();
  }
}
