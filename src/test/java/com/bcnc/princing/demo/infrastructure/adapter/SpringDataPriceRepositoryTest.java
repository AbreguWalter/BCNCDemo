package com.bcnc.princing.demo.infrastructure.adapter;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataPriceRepository;
import com.bcnc.princing.demo.infrastructure.entity.PriceEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpringDataPriceRepositoryIntegrationTest {

    @Autowired
    SpringDataPriceRepository repository;

    @Test
    void whenFindByProductIdAndBrandIdAndEnabledTrueThenReturnsPrices() {
        final List<PriceEntity> result = repository.findByProductIdAndBrandIdAndEnabledTrue(35455L, 1L);
        assertThat(result).isNotEmpty();
    }
}
