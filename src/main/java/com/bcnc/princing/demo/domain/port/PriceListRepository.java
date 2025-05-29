package com.bcnc.princing.demo.domain.port;

import java.util.Optional;

import com.bcnc.princing.demo.domain.model.PriceList;

public interface PriceListRepository {
    Optional<PriceList> findById(Integer id);
}
