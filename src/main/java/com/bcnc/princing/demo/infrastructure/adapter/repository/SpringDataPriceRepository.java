package com.bcnc.princing.demo.infrastructure.adapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.infrastructure.adapter.entity.PriceEntity;

@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByProductIdAndBrandIdAndEnabledTrue(Long productId, Long brandId);
}

