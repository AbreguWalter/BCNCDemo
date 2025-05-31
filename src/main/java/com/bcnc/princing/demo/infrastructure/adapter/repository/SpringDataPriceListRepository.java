package com.bcnc.princing.demo.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcnc.princing.demo.infrastructure.entity.PriceListEntity;

public interface SpringDataPriceListRepository extends JpaRepository<PriceListEntity, Integer> {
  // Métodos custom si necesitas
}
