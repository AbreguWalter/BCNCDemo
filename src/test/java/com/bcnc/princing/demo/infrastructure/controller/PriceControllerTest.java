package com.bcnc.princing.demo.infrastructure.controller;

import com.bcnc.princing.demo.TestConfig.NoSecurityTestConfig;
import com.bcnc.princing.demo.application.service.PriceService;
import com.bcnc.princing.demo.domain.model.Price;
import com.bcnc.princing.demo.infrastructure.mapper.PriceResponseMapper;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PriceController.class)
@Import({PriceResponseMapper.class, NoSecurityTestConfig.class, PriceControllerTest.MockConfig.class})
class PriceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PriceService priceService;

  @TestConfiguration
  static class MockConfig {
    @Bean
    public PriceService priceService() {
      return Mockito.mock(PriceService.class);
    }
  }

  private static final Long PRODUCT_ID = 35455L;
  private static final Long BRAND_ID = 1L;
  private static final String PRODUCT = "Product 35455";
  private static final String BRAND = "ZARA";
  private static final String CURRENCY = "EUR";

  private Price buildPrice(LocalDateTime date, int priority, BigDecimal price, String priceList) {
    return new Price(
        BRAND,
        date.minusHours(1),
        date.plusHours(1),
        1,
        1L,
        priority,
        price,
        CURRENCY
    );
  }

  @Nested
  @DisplayName("Functional API Tests")
  class FunctionalTests {

    @Test
    @DisplayName("Test 1: 14/06 10:00")
    void test1_requestAt10Am14June() throws Exception {
      LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
      when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
          .thenReturn(Optional.of(buildPrice(date, 0, new BigDecimal("35.50"), "Base price list")));
      mockMvc.perform(get("/api/v1/prices")
              .param("date", "2020-06-14T10:00:00")
              .param("productId", PRODUCT_ID.toString())
              .param("brandId", BRAND_ID.toString())
              .header("X-Request-ID", "req-1")
              .header("X-Correlation-ID", "corr-1")
              .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.product").value(PRODUCT))
          .andExpect(jsonPath("$.brand").value(BRAND))
          .andExpect(jsonPath("$.priceList").value("Base price list"))
          .andExpect(jsonPath("$.price").value(35.50))
          .andExpect(jsonPath("$.currency").value(CURRENCY));
    }

    @Test
    @DisplayName("Test 2: 14/06 16:00")
    void test2_requestAt16Pm14June() throws Exception {
      LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
      when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
          .thenReturn(Optional.of(buildPrice(date, 1, new BigDecimal("25.45"), "Afternoon promo")));
      mockMvc.perform(get("/api/v1/prices")
              .param("date", "2020-06-14T16:00:00")
              .param("productId", PRODUCT_ID.toString())
              .param("brandId", BRAND_ID.toString())
              .header("X-Request-ID", "req-2")
              .header("X-Correlation-ID", "corr-2")
              .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.product").value(PRODUCT))
          .andExpect(jsonPath("$.brand").value(BRAND))
          .andExpect(jsonPath("$.priceList").value("Afternoon promo"))
          .andExpect(jsonPath("$.price").value(25.45))
          .andExpect(jsonPath("$.currency").value(CURRENCY));
    }

    @Test
    @DisplayName("Test 3: 14/06 21:00")
    void test3_requestAt21Pm14June() throws Exception {
      LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
      when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
          .thenReturn(Optional.of(buildPrice(date, 0, new BigDecimal("35.50"), "Base price list")));
      mockMvc.perform(get("/api/v1/prices")
              .param("date", "2020-06-14T21:00:00")
              .param("productId", PRODUCT_ID.toString())
              .param("brandId", BRAND_ID.toString())
              .header("X-Request-ID", "req-3")
              .header("X-Correlation-ID", "corr-3")
              .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.product").value(PRODUCT))
          .andExpect(jsonPath("$.brand").value(BRAND))
          .andExpect(jsonPath("$.priceList").value("Base price list"))
          .andExpect(jsonPath("$.price").value(35.50))
          .andExpect(jsonPath("$.currency").value(CURRENCY));
    }

    @Test
    @DisplayName("Test 4: 15/06 10:00")
    void test4_requestAt10Am15June() throws Exception {
      LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
      when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
          .thenReturn(Optional.of(buildPrice(date, 1, new BigDecimal("30.50"), "Morning promo")));
      mockMvc.perform(get("/api/v1/prices")
              .param("date", "2020-06-15T10:00:00")
              .param("productId", PRODUCT_ID.toString())
              .param("brandId", BRAND_ID.toString())
              .header("X-Request-ID", "req-4")
              .header("X-Correlation-ID", "corr-4")
              .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.product").value(PRODUCT))
          .andExpect(jsonPath("$.brand").value(BRAND))
          .andExpect(jsonPath("$.priceList").value("Morning promo"))
          .andExpect(jsonPath("$.price").value(30.50))
          .andExpect(jsonPath("$.currency").value(CURRENCY));
    }

    @Test
    @DisplayName("Test 5: 16/06 21:00")
    void test5_requestAt21Pm16June() throws Exception {
      LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
      when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
          .thenReturn(Optional.of(buildPrice(date, 1, new BigDecimal("38.95"), "Night promo")));
      mockMvc.perform(get("/api/v1/prices")
              .param("date", "2020-06-16T21:00:00")
              .param("productId", PRODUCT_ID.toString())
              .param("brandId", BRAND_ID.toString())
              .header("X-Request-ID", "req-5")
              .header("X-Correlation-ID", "corr-5")
              .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.product").value(PRODUCT))
          .andExpect(jsonPath("$.brand").value(BRAND))
          .andExpect(jsonPath("$.priceList").value("Night promo"))
          .andExpect(jsonPath("$.price").value(38.95))
          .andExpect(jsonPath("$.currency").value(CURRENCY));
    }
  }

  @Test
  void whenPriceNotFound_thenReturns404() throws Exception {
    LocalDateTime date = LocalDateTime.of(2020, 6, 17, 10, 0);
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
