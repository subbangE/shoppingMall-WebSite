package com.myapp.team.option;

import com.myapp.team.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OptionController {

    private final OptionService optionService;
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

//    @PostMapping("/option/insert")
//    public String insertOption(Product product, Option option) {
//        optionService.insertOption(product, option);
//        return "redirect:/prod/list";
//    }

    @PostMapping("/option/update")
    public String updateOption(Option option) {
        optionService.updateOption(option);
        return "redirect:/prod/list";
    }

    @PostMapping("/option/delete")
    public String deleteOption(int optionNo) {
        optionService.deleteOption(optionNo);
        return "redirect:/prod/list";
    }
}
