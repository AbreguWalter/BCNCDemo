package com.bcnc.princing.demo.infrastructure.adapter.mapper;

import org.springframework.stereotype.Component;

import com.bcnc.princing.demo.domain.model.Product;
import com.bcnc.princing.demo.infrastructure.adapter.entity.ProductEntity;

@Component
public class ProductMapper {
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(entity.getId(), entity.getName(), entity.isEnabled());
    }
}
