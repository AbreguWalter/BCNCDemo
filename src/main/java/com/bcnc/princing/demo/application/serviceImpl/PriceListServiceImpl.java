package com.bcnc.princing.demo.application.serviceImpl;

import org.springframework.stereotype.Service;

import com.bcnc.princing.demo.application.service.PriceListService;
import com.bcnc.princing.demo.domain.model.PriceList;
import com.bcnc.princing.demo.domain.port.PriceListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceListServiceImpl implements PriceListService {

    private final PriceListRepository priceListRepository;

    @Override
    public boolean isPriceListEnabled(Integer id) {
        return priceListRepository.findById(id)
            .map(PriceList::enabled)
            .orElse(false);
    }
}
