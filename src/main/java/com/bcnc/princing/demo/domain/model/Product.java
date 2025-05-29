package com.bcnc.princing.demo.domain.model;

public record Product(
        Long id,
        String name,
        boolean enabled
) { }
