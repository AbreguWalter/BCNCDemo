package com.bcnc.princing.demo.domain.port;

import java.util.Optional;

import com.bcnc.princing.demo.domain.model.Currency;

public interface CurrencyRepository {
    Optional<Currency> findBySymbol(String symbol);
}
