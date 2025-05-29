package com.bcnc.princing.demo.infrastructure.adapter;

import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataPriceRepository;
import com.bcnc.princing.demo.infrastructure.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpringDataPriceRepositoryIntegrationTest {

  @Autowired
  SpringDataPriceRepository repository;

  @Test
  void whenFindByProductIdAndBrandIdAndEnabledTrue_thenReturnsPrices() {
    List<PriceEntity> result = repository.findByProductIdAndBrandIdAndEnabledTrue(35455L, 1L);
    assertThat(result).isNotEmpty();
  }
}