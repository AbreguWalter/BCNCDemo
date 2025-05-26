package com.bcnc.princing.demo.infrastructure.adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bcnc.princing.demo.infrastructure.entity.PriceEntity;

@SuppressWarnings("checkstyle:LineLength")
@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
                        SELECT p FROM PriceEntity p
                        WHERE
                            p.product.id = :productId AND
                            p.brand.id = :brandId AND
                            p.startDate <= :startOfDay AND
                            p.endDate >= :startOfDay AND
                            p.enabled = true AND
                            p.product.enabled = true AND
                            p.brand.enabled = true AND
                            p.priceList.enabled = true AND
                            p.currency.enabled = true
                        ORDER BY p.priority DESC
                        """)
    List<PriceEntity> findApplicablePrice(
                @Param("startOfDay") LocalDateTime startOfDay,
                @Param("productId") Long productId,
                @Param("brandId") Long brandId
    );
}

