package com.bcnc.princing.demo.application.usecaseImpl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.bcnc.princing.demo.application.usecase.BrandService;
import com.bcnc.princing.demo.application.usecase.CurrencyService;
import com.bcnc.princing.demo.application.usecase.PriceListService;
import com.bcnc.princing.demo.application.usecase.PriceService;
import com.bcnc.princing.demo.application.usecase.ProductService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    private final BrandService brandService;

    private final ProductService productService;

    private final PriceListService priceListService;

    private final CurrencyService currencyService;

    @Override
    public Optional<Price> getApplicablePrice(LocalDateTime inputDateTime, Long productId, Long brandId) {
        final String correlationId = MDC.get("correlationId");
        log.info("[{}] Looking up price for productId={}, brandId={}", correlationId, productId, brandId);

        // 1. Buscar todos los precios posibles
        final List<Price> prices = priceRepository.findByProductIdAndBrandId(productId, brandId);

        // 2. Filtrar por validez en la fecha y priorizar
        final Optional<Price> maybePrice = prices.stream()
                    .filter(price -> price.isValidFor(inputDateTime))
                    .max(Comparator.comparingInt(Price::priority));

        // 3. Validar enabled SOLO si hay price válido
        if (maybePrice.isEmpty()) {
            log.info("[{}] No valid price found for given date.", correlationId);
            return Optional.empty();
        }

        final Price price = maybePrice.get();

        if (!areEntitiesEnabled(price, brandId)) {
            log.warn("[{}] Entities for price {} are not all enabled. Discarding price.", correlationId, price);
            return Optional.empty();
        }

        return Optional.of(price);
    }

    // Lógica extraída para validar enabled
    private boolean areEntitiesEnabled(Price price, Long brandId) {
        return brandService.isBrandEnabled(brandId) &&
            productService.isProductEnabled(price.productId()) &&
            priceListService.isPriceListEnabled(price.priceListId()) &&
            currencyService.isCurrencyEnabled(price.currency());
    }
}

