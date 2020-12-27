package com.dutran.bakery.controller;


import com.dutran.bakery.entity.Basket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BasketController.class)
public class BasketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Basket basket;

    @Test
    void addsItemsToBasket() throws Exception {
        String expectedSku = "rv";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket").param("sku", expectedSku))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        verify(basket).add(expectedSku);
    }

    @Test
    void removesItemsFromBasket() throws Exception {
        String expectedSku = "rv";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket/delete").param("sku", expectedSku))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/basket"));

        verify(basket).remove(expectedSku);
    }

    @Test
    void showsBasket() {
//        BasketItem basketItem1 = new BasketItem(new ItemDTO("test1", "Test 1", BigDecimal.valueOf(3)), 2);
//        BasketItem basketItem2 = new BasketItem(new ItemDTO("test2", "Test 2", BigDecimal.valueOf(5)), 1);
//        when(basket.getItems()).thenReturn(Arrays.asList(basketItem1, basketItem2));

//        BrowserClient client = new BrowserClient(mockMvc);
//        client.goToBasket();
//
//        assertThat(client.getBasketItemQtyLabel("Test 1")).isEqualTo("2");
//        assertThat(client.getBasketItemQtyLabel("Test 2")).isEqualTo("1");
    }

}