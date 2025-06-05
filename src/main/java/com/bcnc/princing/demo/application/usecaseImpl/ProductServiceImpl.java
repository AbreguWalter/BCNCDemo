package com.bcnc.princing.demo.application.usecaseImpl;

import org.springframework.stereotype.Service;

import com.bcnc.princing.demo.application.usecase.ProductService;
import com.bcnc.princing.demo.domain.model.Product;
import com.bcnc.princing.demo.domain.port.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public boolean isProductEnabled(Long id) {
        return productRepository.findById(id)
            .map(Product::enabled)
            .orElse(false);
    }
}
