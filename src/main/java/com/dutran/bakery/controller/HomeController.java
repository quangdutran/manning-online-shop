package com.dutran.bakery.controller;

import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ICatalogService catalogService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<ItemDTO> itemsForHomePage = catalogService.getItemsForHomeSite();
        model.addAttribute("items", itemsForHomePage);
        return "index";
    }
}
