package com.bcnc.princing.demo.domain.model;

public record Currency(
        Long id,
        String symbol,
        boolean enabled
) { }

