package com.bcnc.princing.demo.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.infrastructure.adapter.entity.BrandEntity;

@Repository
public interface SpringDataBrandRepository extends JpaRepository<BrandEntity, Long> {

}
