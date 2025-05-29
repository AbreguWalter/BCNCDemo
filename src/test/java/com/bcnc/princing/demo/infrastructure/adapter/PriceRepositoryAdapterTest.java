package com.bcnc.princing.demo.infrastructure.adapter;

import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataPriceRepository;
import com.bcnc.princing.demo.infrastructure.entity.PriceEntity;
import com.bcnc.princing.demo.infrastructure.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceRepositoryAdapterTest {

  @Test
  void shouldMapEntitiesToDomain() {
    SpringDataPriceRepository jpaRepo = Mockito.mock(SpringDataPriceRepository.class);
    PriceMapper mapper = Mockito.mock(PriceMapper.class);
    PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(jpaRepo, mapper);

    PriceEntity entity = new PriceEntity();
    Price domain = new Price("ZARA", null, null, 1, 1L, 1, BigDecimal.TEN, "EUR");

    Mockito.when(jpaRepo.findByProductIdAndBrandIdAndEnabledTrue(35455L, 1L)).thenReturn(List.of(entity));
    Mockito.when(mapper.toDomain(entity)).thenReturn(domain);

    List<Price> result = adapter.findByProductIdAndBrandId(35455L, 1L);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).brand()).isEqualTo("ZARA");
  }
}
