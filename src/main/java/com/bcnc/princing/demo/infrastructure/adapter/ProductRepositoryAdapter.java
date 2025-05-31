package com.bcnc.princing.demo.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.domain.model.Product;
import com.bcnc.princing.demo.domain.port.ProductRepository;
import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataProductRepository;
import com.bcnc.princing.demo.infrastructure.entity.ProductEntity;
import com.bcnc.princing.demo.infrastructure.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository jpaRepo;

    private final ProductMapper productMapper;

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepo.findById(id)
            .filter(ProductEntity::isEnabled)
            .map(productMapper::toDomain);
    }
}
