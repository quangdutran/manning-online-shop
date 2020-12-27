package com.dutran.bakery.service;


import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.entity.Item;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CatalogServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @MockBean(ItemRepository.class)
    ItemRepository itemRepository;

    @InjectMocks
    CatalogServiceImpl underTest;

    @Ignore
    @DisplayName("Service returns mapped data from database")
    public void getItemsForHomeSite_returnDataFromDB() {
        String expectedSku = "TEST-01";
        String expectedName = "Hause";
        String expectedDesc = "My desc";
        double price = 2.35;
        BigDecimal expectedPrice = new BigDecimal(price);

        saveItem(expectedSku, expectedName, expectedDesc, expectedPrice);

        List<ItemDTO> result = underTest.getItemsForHomeSite();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(expectedName);
        assertThat(result.get(0).getDescription()).isEqualTo(expectedDesc);
        assertThat(result.get(0).getPrice()).isEqualTo("$" + price);
        assertThat(result.get(0).getSku()).isEqualTo(expectedSku);
    }

    void saveItem(String sku, String name, String desc, BigDecimal price) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(desc);
        item.setPrice(price);
        item.setSku(sku);

        testEntityManager.persistAndFlush(item);
    }
}
