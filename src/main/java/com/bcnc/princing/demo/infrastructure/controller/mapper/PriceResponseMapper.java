package com.bcnc.princing.demo.infrastructure.controller.mapper;

import org.springframework.stereotype.Component;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.controller.dto.PriceResponse;

@Component
public class PriceResponseMapper {

    public PriceResponse toResponse(Price price) {
        return new PriceResponse(
            price.productId(),
            price.brand(),
            price.priceListId(),
            price.startDate(),
            price.endDate(),
            price.price(),
            price.currency()
        );
    }
}
