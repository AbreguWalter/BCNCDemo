package com.bcnc.princing.demo.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.domain.model.Currency;
import com.bcnc.princing.demo.domain.port.CurrencyRepository;
import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataCurrencyRepository;
import com.bcnc.princing.demo.infrastructure.mapper.CurrencyMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryAdapter implements CurrencyRepository {

    private final SpringDataCurrencyRepository jpaRepo;

    private final CurrencyMapper currencyMapper;

    @Override
    public Optional<Currency> findBySymbol(String symbol) {
        return jpaRepo.findBySymbol(symbol).map(currencyMapper::toDomain);
    }
}
