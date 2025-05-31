package com.bcnc.princing.demo.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.domain.model.PriceList;
import com.bcnc.princing.demo.domain.port.PriceListRepository;
import com.bcnc.princing.demo.infrastructure.adapter.repository.SpringDataPriceListRepository;
import com.bcnc.princing.demo.infrastructure.entity.PriceListEntity;
import com.bcnc.princing.demo.infrastructure.mapper.PriceListMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PriceListRepositoryAdapter implements PriceListRepository {

    private final SpringDataPriceListRepository jpaRepo;

    private final PriceListMapper mapper;

    @Override
    public Optional<PriceList> findById(Integer id) {
        return jpaRepo.findById(id)
          .filter(PriceListEntity::isEnabled)
          .map(mapper::toDomain);
    }
}
