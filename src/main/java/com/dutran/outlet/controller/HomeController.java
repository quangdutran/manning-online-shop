package com.dutran.outlet.controller;

import com.dutran.outlet.dto.ItemDTO;
import com.dutran.outlet.entity.Basket;
import com.dutran.outlet.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ICatalogService catalogService;
    @Autowired
    private Basket basket;

    @GetMapping("/")
    public String homePage(Model model) {
        List<ItemDTO> itemsForHomePage = catalogService.getItemsForHomeSite();
        model.addAttribute("items", itemsForHomePage);
        model.addAttribute("basketTotal", basket.getTotalItems());
        return "index";
    }
}
