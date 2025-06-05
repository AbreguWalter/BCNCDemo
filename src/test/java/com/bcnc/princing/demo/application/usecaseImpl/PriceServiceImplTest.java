package com.bcnc.princing.demo.application.usecaseImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.bcnc.princing.demo.application.usecase.BrandService;
import com.bcnc.princing.demo.application.usecase.CurrencyService;
import com.bcnc.princing.demo.application.usecase.PriceListService;
import com.bcnc.princing.demo.application.usecase.ProductService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;

import static org.assertj.core.api.Assertions.assertThat;

class PriceServiceImplTest {

    @Test
    void givenMultiplePricesWhenGetApplicablePriceThenReturnsWithHighestPriority() {
        final PriceRepository repo = Mockito.mock(PriceRepository.class);
        final BrandService brandService = Mockito.mock(BrandService.class);
        final ProductService productService = Mockito.mock(ProductService.class);
        final PriceListService priceListService = Mockito.mock(PriceListService.class);
        final CurrencyService currencyService = Mockito.mock(CurrencyService.class);

        final PriceServiceImpl service = new PriceServiceImpl(repo, brandService, productService, priceListService, currencyService);

        final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 14, 15, 0);

        final Price lowPriority = new Price("ZARA", LocalDateTime.of(2020, 6, 14, 14, 0),
            LocalDateTime.of(2020, 6, 14, 18, 0), 1, 1L, 0, new BigDecimal("20.00"), "EUR");
        final Price highPriority = new Price("ZARA", LocalDateTime.of(2020, 6, 14, 15, 0),
            LocalDateTime.of(2020, 6, 14, 17, 0), 1, 1L, 2, new BigDecimal("25.45"), "EUR");

        Mockito.when(repo.findByProductIdAndBrandId(35455L, 1L)).thenReturn(List.of(lowPriority, highPriority));
        Mockito.when(brandService.isBrandEnabled(1L)).thenReturn(true);
        Mockito.when(productService.isProductEnabled(1L)).thenReturn(true);
        Mockito.when(priceListService.isPriceListEnabled(1)).thenReturn(true);
        Mockito.when(currencyService.isCurrencyEnabled("EUR")).thenReturn(true);

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
