package com.chandramouli.urlshortner.controller;

import com.chandramouli.urlshortner.entity.Link;
import com.chandramouli.urlshortner.repository.LinkRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/application/shorten")
@RestController
@Slf4j
public class ShortenerController {

    @Autowired
    LinkRepo linkRepo;

    @GetMapping(value = "/{url}")
    @ResponseBody
    public String shortenTheLink(@PathVariable String url) {
        linkRepo.save(new Link(url, url));
        return url;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public String test() {
        log.info("entered the code base");
        return linkRepo.findAll().toString();
    }
}
