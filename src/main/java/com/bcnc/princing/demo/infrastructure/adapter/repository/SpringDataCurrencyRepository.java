package com.bcnc.princing.demo.infrastructure.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcnc.princing.demo.infrastructure.adapter.entity.CurrencyEntity;

public interface SpringDataCurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
  // Métodos personalizados si necesitas, por ejemplo, buscar solo los activos
    Optional<CurrencyEntity> findBySymbol(String symbol);
}
