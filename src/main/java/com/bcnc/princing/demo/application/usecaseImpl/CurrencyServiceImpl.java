package com.bcnc.princing.demo.application.usecaseImpl;

import org.springframework.stereotype.Service;

import com.bcnc.princing.demo.application.usecase.CurrencyService;
import com.bcnc.princing.demo.domain.model.Currency;
import com.bcnc.princing.demo.domain.port.CurrencyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public boolean isCurrencyEnabled(String symbol) {
        return currencyRepository.findBySymbol(symbol)
            .map(Currency::enabled)
            .orElse(false);
    }
}
