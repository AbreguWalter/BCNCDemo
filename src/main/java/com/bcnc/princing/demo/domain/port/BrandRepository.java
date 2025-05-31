package com.bcnc.princing.demo.domain.port;

import java.util.Optional;

import com.bcnc.princing.demo.domain.model.Brand;

public interface BrandRepository {
    Optional<Brand> findById(Long id);
}
