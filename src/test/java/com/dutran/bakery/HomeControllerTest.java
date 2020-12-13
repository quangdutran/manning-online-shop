package com.dutran.bakery;


import com.dutran.bakery.controller.HomeController;
import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.service.ICatalogService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = HomeController.class)
@TestPropertySource("/application.properties")
public class HomeControllerTest {

    @Value("${application.language}")
    private String language;

    @Value("${application.country}")
    private String country;

    private WebClient webClient;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ICatalogService catalogService;

    @BeforeEach
    public void setUp() {
        this.webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc).build();
    }

    @org.junit.jupiter.api.Test
    public void testHomePage() throws IOException {
        String itemName = "New CD";
        String description = "Spinning Records";
        double price = 5.9;
        System.out.println(language);
        mockItems(itemName, description, new BigDecimal(price));
        HtmlPage page = webClient.getPage("http://localhost/");

        assertThat(page.querySelectorAll(".card-title"))
                .anyMatch(domElement -> itemName.equals(domElement.getFirstChild().getNextSibling().asText()));

        DomNodeList<DomNode> itemBox = page.querySelectorAll(".card-body");

        assertThat(page.querySelectorAll(".card-body"))
                .anyMatch(domElement -> {
                    boolean testItemPrice = false;
                    boolean testItemDesc = false;
                    for (DomNode node : domElement.getChildren()) {
                        if (node instanceof HtmlHeading5) {
                            System.out.println(node.asText());
                            testItemPrice = "$5.90".equals(node.asText());
                        }
                        if (node instanceof HtmlParagraph) {
                            testItemDesc = description.equals(node.asText());
                        }
                    }
                    return testItemPrice && testItemDesc;
                });
    }

    private void mockItems(String name, String description, BigDecimal price) {
        when(catalogService.getItemsForHomeSite()).thenReturn(Collections.singletonList(
                ItemDTO.builder().name(name).description(description)
                        .price(NumberFormat.getCurrencyInstance(new Locale(language, country)).format(price)).build()
        ));
    }
}
