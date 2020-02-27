package com.mc.citicraft.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {
    @GetMapping("/loader")
    public String upload() {
        return "upload";
    }
}
