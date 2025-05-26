package com.bcnc.princing.demo.infrastructure.adapter;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.domain.port.PriceRepository;
import com.bcnc.princing.demo.infrastructure.mapper.PriceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final SpringDataPriceRepository jpaRepo;

    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> findValidPriceAtDate(LocalDateTime startOfDay, Long productId, Long brandId) {
        log.debug("Buscando precios para producto {}, marca {}, fecha {}", productId, brandId, startOfDay);

        return jpaRepo
            .findApplicablePrice(startOfDay, productId, brandId)
            .stream()
            .filter(entity -> entity.getStartDate().toLocalDate().equals(startOfDay.toLocalDate()))
            .findFirst()
            .map(priceMapper::toDomain);
    }
}
