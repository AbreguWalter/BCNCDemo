package com.bcnc.princing.demo.domain.port;

import java.util.List;

import com.bcnc.princing.demo.domain.model.Price;

public interface PriceRepository {

    List<Price> findByProductIdAndBrandId(Long productId, Long brandId);
}
