package org.message.comparator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compare")
public class CompareController {

    @GetMapping("")
    public String getMainPage(Model model) {
        model.addAttribute("message", "Test message");
        return "CompareMainPage";
    }
}
