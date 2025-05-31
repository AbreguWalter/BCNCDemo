package com.bcnc.princing.demo.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.domain.model.Brand;
import com.bcnc.princing.demo.domain.port.BrandRepository;
import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataBrandRepository;
import com.bcnc.princing.demo.infrastructure.mapper.BrandMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryAdapter implements BrandRepository {

    private final SpringDataBrandRepository jpaRepo;

    private final BrandMapper brandMapper;

    @Override
    public Optional<Brand> findById(Long id) {
        return jpaRepo.findById(id).map(brandMapper::toDomain);
    }
}
