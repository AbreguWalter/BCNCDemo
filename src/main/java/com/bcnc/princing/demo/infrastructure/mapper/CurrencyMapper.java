package com.bcnc.princing.demo.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.bcnc.princing.demo.domain.model.Currency;
import com.bcnc.princing.demo.infrastructure.entity.CurrencyEntity;

@Component
public class CurrencyMapper {
    public Currency toDomain(CurrencyEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Currency(entity.getId(), entity.getSymbol(), entity.isEnabled());
    }
}
