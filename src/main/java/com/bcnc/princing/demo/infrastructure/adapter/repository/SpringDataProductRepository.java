package com.bcnc.princing.demo.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcnc.princing.demo.infrastructure.entity.ProductEntity;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
  // Puedes agregar métodos customizados si lo necesitas
}
