package com.bcnc.princing.demo.infrastructure.adapter.mapper;

import org.springframework.stereotype.Component;

import com.bcnc.princing.demo.domain.model.PriceList;
import com.bcnc.princing.demo.infrastructure.adapter.entity.PriceListEntity;

@Component
public class PriceListMapper {
    public PriceList toDomain(PriceListEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PriceList(entity.getId(), entity.getName(), entity.isEnabled());
    }
}
