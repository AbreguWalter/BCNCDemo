package com.bcnc.princing.demo.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.bcnc.princing.demo.domain.model.Brand;
import com.bcnc.princing.demo.infrastructure.entity.BrandEntity;

@Component
public class BrandMapper {
    public Brand toDomain(BrandEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Brand(entity.getId(), entity.getName(), entity.isEnabled());
    }
}
