package com.bcnc.princing.demo.application.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.bcnc.princing.demo.application.service.BrandService;
import com.bcnc.princing.demo.application.service.CurrencyService;
import com.bcnc.princing.demo.application.service.PriceListService;
import com.bcnc.princing.demo.application.service.ProductService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;

import static org.assertj.core.api.Assertions.assertThat;

class PriceServiceImplTest {

    @Test
    void givenMultiplePricesWhenGetApplicablePriceThenReturnsWithHighestPriority() {

        final PriceRepository repo = Mockito.mock(PriceRepository.class);
        final BrandService repo1 = Mockito.mock(BrandService.class);
        final ProductService repo2 = Mockito.mock(ProductService.class);
        final PriceListService repo3 = Mockito.mock(PriceListService.class);
        final CurrencyService repo4 = Mockito.mock(CurrencyService.class);

        final PriceServiceImpl service = new PriceServiceImpl(repo, repo1, repo2, repo3, repo4);

        final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        final Price lowPriority = new Price("ZARA", searchDate.minusHours(2), searchDate.plusHours(2), 1,
                1L, 0, new BigDecimal("20.00"), "EUR");
        final Price highPriority = new Price("ZARA", searchDate.minusHours(1), searchDate.plusHours(1), 1,
                1L, 2, new BigDecimal("25.45"), "EUR");

        Mockito.when(repo.findByProductIdAndBrandId(35455L, 1L))
                .thenReturn(List.of(lowPriority, highPriority));

        final Optional<Price> result = service.getApplicablePrice(searchDate, 35455L, 1L);

        assertThat(result).isPresent();
        assertThat(result.get().priority()).isEqualTo(2);
        assertThat(result.get().price()).isEqualTo(new BigDecimal("25.45"));
    }

    @Test
    void whenNoPriceAppliesThenReturnsEmpty() {
        final PriceRepository repo = Mockito.mock(PriceRepository.class);
        final BrandService repo1 = Mockito.mock(BrandService.class);
        final ProductService repo2 = Mockito.mock(ProductService.class);
        final PriceListService repo3 = Mockito.mock(PriceListService.class);
        final CurrencyService repo4 = Mockito.mock(CurrencyService.class);

        final PriceServiceImpl service = new PriceServiceImpl(repo, repo1, repo2, repo3, repo4);

        Mockito.when(repo.findByProductIdAndBrandId(35455L, 1L)).thenReturn(List.of());

        final Optional<Price> result = service.getApplicablePrice(LocalDateTime.now(), 35455L, 1L);

        assertThat(result).isEmpty();
    }
}
