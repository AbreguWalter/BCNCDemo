package com.bcnc.princing.demo.infrastructure.controller;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bcnc.princing.demo.TestConfig.NoSecurityTestConfig;
import com.bcnc.princing.demo.application.service.PriceService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.mapper.PriceResponseMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConfiguration
class MockServiceConfig {
    @Bean
    public PriceService priceService() {
        return Mockito.mock(PriceService.class);
    }
}

@WebMvcTest(controllers = PriceController.class)
@Import({PriceResponseMapper.class, MockServiceConfig.class, NoSecurityTestConfig.class})
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceService priceService;

    private static final Long PRODUCT_ID = 35455L;

    private static final Long BRAND_ID = 1L;

    private static final String BRAND = "ZARA";

    private static final String CURRENCY = "EUR";

    private Price buildPrice(
            final String brand,
            final LocalDateTime startDate,
            final LocalDateTime endDate,
            final int priceList,
            final Long productId,
            final int priority,
            final BigDecimal price,
            final String currency
    ) {
        return new Price(
                brand,
                startDate,
                endDate,
                priceList,
                productId,
                priority,
                price,
                currency
        );
    }

    @Nested
    @DisplayName("Functional API Tests")
    class FunctionalTests {

        @Test
        @DisplayName("Test 1: 14/06 10:00")
        void test1RequestAt10Am14June() throws Exception {
            final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 14, 10, 0);
            Price expected = buildPrice(
                    BRAND,
                    LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                    LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                    1,
                    PRODUCT_ID,
                    0,
                    new BigDecimal("35.50"),
                    CURRENCY
            );
            when(priceService.getApplicablePrice(searchDate, PRODUCT_ID, BRAND_ID))
                    .thenReturn(Optional.of(expected));
            mockMvc.perform(get("/api/v1/prices")
                            .param("date", "2020-06-14T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString())
                            .header("X-Request-ID", "req-1")
                            .header("X-Correlation-ID", "corr-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.product").value(PRODUCT_ID.intValue()))
                    .andExpect(jsonPath("$.brand").value(BRAND))
                    .andExpect(jsonPath("$.priceList").value(1))
                    .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                    .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                    .andExpect(jsonPath("$.price").value(35.50))
                    .andExpect(jsonPath("$.currency").value(CURRENCY));
        }

        @Test
        @DisplayName("Test 2: 14/06 16:00")
        void test2RequestAt16Pm14June() throws Exception {
            final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 14, 16, 0);
            Price expected = buildPrice(
                    BRAND,
                    LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                    LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                    2,
                    PRODUCT_ID,
                    1,
                    new BigDecimal("25.45"),
                    CURRENCY
            );
            when(priceService.getApplicablePrice(searchDate, PRODUCT_ID, BRAND_ID))
                    .thenReturn(Optional.of(expected));
            mockMvc.perform(get("/api/v1/prices")
                            .param("date", "2020-06-14T16:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString())
                            .header("X-Request-ID", "req-1")
                            .header("X-Correlation-ID", "corr-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.product").value(PRODUCT_ID.intValue()))
                    .andExpect(jsonPath("$.brand").value(BRAND))
                    .andExpect(jsonPath("$.priceList").value(2))
                    .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                    .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                    .andExpect(jsonPath("$.price").value(25.45))
                    .andExpect(jsonPath("$.currency").value(CURRENCY));
        }

        @Test
        @DisplayName("Test 3: 14/06 21:00")
        void test3RequestAt21Pm14June() throws Exception {
            final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 14, 21, 0);
            Price expected = buildPrice(
                    BRAND,
                    LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                    LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                    1,
                    PRODUCT_ID,
                    0,
                    new BigDecimal("35.50"),
                    CURRENCY
            );
            when(priceService.getApplicablePrice(searchDate, PRODUCT_ID, BRAND_ID))
                    .thenReturn(Optional.of(expected));
            mockMvc.perform(get("/api/v1/prices")
                            .param("date", "2020-06-14T21:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString())
                            .header("X-Request-ID", "req-1")
                            .header("X-Correlation-ID", "corr-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.product").value(PRODUCT_ID.intValue()))
                    .andExpect(jsonPath("$.brand").value(BRAND))
                    .andExpect(jsonPath("$.priceList").value(1))
                    .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                    .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                    .andExpect(jsonPath("$.price").value(35.50))
                    .andExpect(jsonPath("$.currency").value(CURRENCY));
        }

        @Test
        @DisplayName("Test 4: 15/06 10:00")
        void test4RequestAt10Am15June() throws Exception {
            final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 15, 10, 0);
            Price expected = buildPrice(
                    BRAND,
                    LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                    LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                    3,
                    PRODUCT_ID,
                    1,
                    new BigDecimal("30.50"),
                    CURRENCY
            );
            when(priceService.getApplicablePrice(searchDate, PRODUCT_ID, BRAND_ID))
                    .thenReturn(Optional.of(expected));
            mockMvc.perform(get("/api/v1/prices")
                            .param("date", "2020-06-15T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString())
                            .header("X-Request-ID", "req-1")
                            .header("X-Correlation-ID", "corr-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.product").value(PRODUCT_ID.intValue()))
                    .andExpect(jsonPath("$.brand").value(BRAND))
                    .andExpect(jsonPath("$.priceList").value(3))
                    .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
                    .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
                    .andExpect(jsonPath("$.price").value(30.50))
                    .andExpect(jsonPath("$.currency").value(CURRENCY));
        }

        @Test
        @DisplayName("Test 5: 16/06 21:00")
        void test5RequestAt21Pm16June() throws Exception {
            final LocalDateTime searchDate = LocalDateTime.of(2020, 6, 16, 21, 0);
            Price expected = buildPrice(
                    BRAND,
                    LocalDateTime.of(2020, 6, 15, 16, 0, 0),
                    LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                    4,
                    PRODUCT_ID,
                    1,
                    new BigDecimal("38.95"),
                    CURRENCY
            );
            when(priceService.getApplicablePrice(searchDate, PRODUCT_ID, BRAND_ID))
                    .thenReturn(Optional.of(expected));
            mockMvc.perform(get("/api/v1/prices")
                            .param("date", "2020-06-16T21:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString())
                            .header("X-Request-ID", "req-1")
                            .header("X-Correlation-ID", "corr-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.product").value(PRODUCT_ID.intValue()))
                    .andExpect(jsonPath("$.brand").value(BRAND))
                    .andExpect(jsonPath("$.priceList").value(4))
                    .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
                    .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                    .andExpect(jsonPath("$.price").value(38.95))
                    .andExpect(jsonPath("$.currency").value(CURRENCY));
        }
    }

    @Test
    void whenPriceNotFoundThenReturns404() throws Exception {
        final LocalDateTime date = LocalDateTime.of(2020, 6, 17, 10, 0);
        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/prices")
                        .param("date", "2020-06-17T10:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .header("X-Request-ID", "abc123")
                        .header("X-Correlation-ID", "trace-001")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}

