package com.bcnc.princing.demo.domain.port;

import java.util.Optional;

import com.bcnc.princing.demo.domain.model.Product;

public interface ProductRepository {
    Optional<Product> findById(Long id);
}
