package com.bcnc.princing.demo.application.usecaseImpl;

import org.springframework.stereotype.Service;

import com.bcnc.princing.demo.application.usecase.BrandService;
import com.bcnc.princing.demo.domain.model.Brand;
import com.bcnc.princing.demo.domain.port.BrandRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public boolean isBrandEnabled(Long brandId) {
        return brandRepository.findById(brandId)
            .map(Brand::enabled)
            .orElse(false);
    }
}
