package com.mc.citicraft.web.controller;

import com.mc.citicraft.web.config.SwaggerDiscoverable;
import com.mc.citicraft.web.service.ConnectorServiceResult;
import com.mc.citicraft.web.service.IConnectorService;
import com.mc.citicraft.web.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
@SwaggerDiscoverable
public class ConnectorController {

    @Autowired
    private IConnectorService service;

    @Autowired
    private IFileService fileService;

    @PostMapping("uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file) {
        fileService.uploadFile(file);
        return "uploaded. number of cities = " + service.citiesCount();
    }

    @GetMapping(value = "/check2")
    public ConnectorServiceResult checkConnection2(@RequestParam("from") final String from, @RequestParam("to") final String to) {
        return service.work(from, to);
    }

    @GetMapping(value = "/check")
    public String checkConnection(@RequestParam("from") final String from, @RequestParam("to") final String to) {
        return service.work(from, to).toString();
    }


}
