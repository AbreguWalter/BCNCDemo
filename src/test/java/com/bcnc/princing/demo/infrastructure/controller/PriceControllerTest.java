package com.bcnc.princing.demo.infrastructure.controller;

import com.bcnc.princing.demo.application.service.PriceService;
import com.bcnc.princing.demo.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
@Import(PriceControllerTest.MockConfig.class)
class PriceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PriceService priceService; // este es el @Bean mockeado

  @TestConfiguration
  static class MockConfig {
    @Bean
    public PriceService priceService() {
      return Mockito.mock(PriceService.class);
    }
  }

  @BeforeEach
  void setup() {
    Mockito.reset(priceService);
  }

  @Test
  void givenValidParams_whenPriceFound_thenReturnsOk() throws Exception {
    // Given
    LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0);
    String brand = "ZARA";

    Price expectedPrice = new Price(
        brand,
        dateTime.minusHours(1),
        dateTime.plusHours(1),
        "Afternoon promo",
        "Product 35455",
        1,
        new BigDecimal("25.45"),
        "EUR"
    );

    Mockito.when(priceService.getApplicablePrice(dateTime, 35455L, 1L))
        .thenReturn(Optional.of(expectedPrice));

    // When & Then
    mockMvc.perform(get("/api/prices")
            .param("date", "2020-06-14T16:00:00")
            .param("productId", "35455")
            .param("brandId", "1")
            .header("X-Request-ID", "test-req")
            .header("X-Correlation-ID", "test-corr")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.product").value("Product 35455"))
        .andExpect(jsonPath("$.brand").value("ZARA"))
        .andExpect(jsonPath("$.priceList").value("Afternoon promo"))
        .andExpect(jsonPath("$.price").value(25.45))
        .andExpect(jsonPath("$.currency").value("EUR"));
  }

  @Test
  void givenValidParams_whenNoPriceFound_thenReturns404() throws Exception {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

    Mockito.when(priceService.getApplicablePrice(eq(date), eq(35455L), eq(1L)))
        .thenReturn(Optional.empty());

    // When & Then
    mockMvc.perform(get("/api/prices")
            .param("date", "2020-06-14T16:00:00")
            .param("productId", "35455")
            .param("brandId", "1"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("NOT_FOUND"));
  }

  @Test
  void givenJune14At10_whenGetPrice_thenReturnBasePrice() throws Exception {
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
    setupMockForTime(date, "Base price list", 35.50);

    performAndValidate(date, "Base price list", 35.50);
  }

  @Test
  void givenJune14At16_whenGetPrice_thenReturnAfternoonPromo() throws Exception {
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
    setupMockForTime(date, "Afternoon promo", 25.45);

    performAndValidate(date, "Afternoon promo", 25.45);
  }

  @Test
  void givenJune14At21_whenGetPrice_thenReturnBasePrice() throws Exception {
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
    setupMockForTime(date, "Base price list", 35.50);

    performAndValidate(date, "Base price list", 35.50);
  }

  @Test
  void givenJune15At10_whenGetPrice_thenReturnMorningPromo() throws Exception {
    LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
    setupMockForTime(date, "Morning promo", 30.50);

    performAndValidate(date, "Morning promo", 30.50);
  }

  @Test
  void givenJune16At21_whenGetPrice_thenReturnEveningPromo() throws Exception {
    LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
    setupMockForTime(date, "Evening promo", 38.95);

    performAndValidate(date, "Evening promo", 38.95);
  }

  private void setupMockForTime(LocalDateTime date, String priceListName, double priceValue) {
    Price price = new Price(
        "ZARA",
        date.minusHours(1),
        date.plusHours(1),
        priceListName,
        "Product 35455",
        1,
        BigDecimal.valueOf(priceValue),
        "EUR"
    );
    Mockito.when(priceService.getApplicablePrice(date, 35455L, 1L))
        .thenReturn(Optional.of(price));
  }

  private void performAndValidate(LocalDateTime date, String expectedPriceList, double expectedPrice) throws Exception {
    mockMvc.perform(get("/api/prices")
            .param("date", date.toString())
            .param("productId", "35455")
            .param("brandId", "1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.product").value("Product 35455"))
        .andExpect(jsonPath("$.brand").value("ZARA"))
        .andExpect(jsonPath("$.priceList").value(expectedPriceList))
        .andExpect(jsonPath("$.price").value(expectedPrice))
        .andExpect(jsonPath("$.currency").value("EUR"));
  }
}
