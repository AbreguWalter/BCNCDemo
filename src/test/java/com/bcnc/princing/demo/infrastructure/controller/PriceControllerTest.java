package com.bcnc.princing.demo.infrastructure.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.bcnc.princing.demo.config.DemoPricingApplication.class)
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Functional API Tests")
    class FunctionalTests {

        @Test
        @DisplayName("Test 1: 14/06 10:00")
        void test1RequestAt10Am14June() throws Exception {
            mockMvc.perform(get("/api/v1/prices")
                    .param("date", "2020-06-14T10:00:00")
                    .param("productId", "35455")
                    .param("brandId", "1")
                    .header("X-Request-ID", "req-1")
                    .header("X-Correlation-ID", "corr-1")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("35455"))
                .andExpect(jsonPath("$.brand").value("ZARA"))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
        }

        @Test
        @DisplayName("Test 2: 14/06 16:00")
        void test2RequestAt16Pm14June() throws Exception {
            mockMvc.perform(get("/api/v1/prices")
                    .param("date", "2020-06-14T16:00:00")
                    .param("productId", "35455")
                    .param("brandId", "1")
                    .header("X-Request-ID", "req-2")
                    .header("X-Correlation-ID", "corr-2")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("35455"))
                .andExpect(jsonPath("$.brand").value("ZARA"))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
        }

        @Test
        @DisplayName("Test 3: 14/06 21:00")
        void test3RequestAt21Pm14June() throws Exception {
            mockMvc.perform(get("/api/v1/prices")
                    .param("date", "2020-06-14T21:00:00")
                    .param("productId", "35455")
                    .param("brandId", "1")
                    .header("X-Request-ID", "req-3")
                    .header("X-Correlation-ID", "corr-3")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("35455"))
                .andExpect(jsonPath("$.brand").value("ZARA"))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
        }

        @Test
        @DisplayName("Test 4: 15/06 10:00")
        void test4RequestAt10Am15June() throws Exception {
            mockMvc.perform(get("/api/v1/prices")
                    .param("date", "2020-06-15T10:00:00")
                    .param("productId", "35455")
                    .param("brandId", "1")
                    .header("X-Request-ID", "req-4")
                    .header("X-Correlation-ID", "corr-4")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("35455"))
                .andExpect(jsonPath("$.brand").value("ZARA"))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
        }

        @Test
        @DisplayName("Test 5: 16/06 21:00")
        void test5RequestAt21Pm16June() throws Exception {
            mockMvc.perform(get("/api/v1/prices")
                    .param("date", "2020-06-16T21:00:00")
                    .param("productId", "35455")
                    .param("brandId", "1")
                    .header("X-Request-ID", "req-5")
                    .header("X-Correlation-ID", "corr-5")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("35455"))
                .andExpect(jsonPath("$.brand").value("ZARA"))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));
        }
    }

    @Test
    void whenPriceNotFoundThenReturns404() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                .param("date", "2021-06-17T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .header("X-Request-ID", "abc123")
                .header("X-Correlation-ID", "trace-001")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
