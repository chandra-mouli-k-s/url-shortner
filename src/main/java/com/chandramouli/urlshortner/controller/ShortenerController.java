package com.chandramouli.urlshortner.controller;

import com.chandramouli.urlshortner.entity.Link;
import com.chandramouli.urlshortner.repository.LinkRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@RestController
@Slf4j
public class ShortenerController {

    @Autowired
    LinkRepo linkRepo;

    @GetMapping(value = "/shorten")
    @ResponseBody
    public Link shortenTheLink(@RequestParam(value = "url") String url) throws Exception {
        if (UrlValidator.getInstance().isValid(url)) {
            String shortUrl = DigestUtils.md5DigestAsHex(url.getBytes(StandardCharsets.UTF_8));
            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            linkRepo.save(new Link(shortUrl, url));
            return new Link(baseUrl + "/" + shortUrl, url);
        } else throw new Exception("Invalid URL");
    }

    @GetMapping(value = "/{shortLink}")
    @ResponseBody
    public String getFullUrl(@PathVariable String shortLink) throws Exception {
        Optional<Link> link = linkRepo.findById(shortLink);
        if (link.isPresent())
            return link.get().getActualUrl();
        else
            throw new Exception("No Such shortened URL exists");
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public List<Link> test() {
        log.info("entered the code base");
        return linkRepo.findAll();
    }

}
