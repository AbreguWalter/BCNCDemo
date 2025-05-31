package com.bcnc.princing.demo.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.entity.PriceEntity;

@Component
public class PriceMapper {

    public Price toDomain(PriceEntity entity) {
        return new Price(
            entity.getBrand().getName(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getPriceList().getId(),
            entity.getProduct().getId(),
            entity.getPriority(),
            entity.getPrice(),
            entity.getCurrency().getSymbol()
        );
    }
}
