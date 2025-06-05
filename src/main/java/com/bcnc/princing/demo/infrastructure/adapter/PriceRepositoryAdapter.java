package com.bcnc.princing.demo.infrastructure.adapter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;
import com.bcnc.princing.demo.infrastructure.adapter.mapper.PriceMapper;
import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataPriceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final SpringDataPriceRepository jpaRepo;

    private final PriceMapper mapper;

    @Override
    public List<Price> findByProductIdAndBrandId(Long productId, Long brandId) {
        log.debug("Buscando precios para producto {}, marca {}", productId, brandId);
        return jpaRepo.findByProductIdAndBrandIdAndEnabledTrue(productId, brandId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
}
