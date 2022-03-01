package com.chandramouli.urlshortner.controller;

import com.chandramouli.urlshortner.entity.Link;
import com.chandramouli.urlshortner.repository.LinkRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/")
@RestController
@Slf4j
public class ShortenerController {

    @Autowired
    LinkRepo linkRepo;

    @GetMapping(value = "/shorten")
    @ResponseBody
    public Link shortenTheLink(@RequestParam(value = "url") String url) throws Exception {
        log.debug("Inside shortenTheLink method");
        try {
            if (UrlValidator.getInstance().isValid(url)) {
                log.info("User entered a valid URL: " + url);
                String shortUrl = DigestUtils.md5DigestAsHex(url.getBytes(StandardCharsets.UTF_8));
                String reUsedLink = getIfAlreadyShortened(shortUrl);
                final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                if (reUsedLink == null) {
                    log.debug("This is a new URL to be shortened");
                    linkRepo.save(new Link(shortUrl, url));
                    log.info("Saved the shortened URL in the backend");
                }
                return new Link(baseUrl + "/" + shortUrl, url);
            } else throw new Exception("Invalid URL");
        } catch (Exception ex) {
            log.error("Exception occurred while shortening the URL", ex);
            throw ex;
        }
    }

    @GetMapping(value = "/{shortLink}")
    @ResponseBody
    public RedirectView getFullUrl(@PathVariable String shortLink) throws Exception {
        log.debug("Inside getFullUrl method");
        try {
            String actualUrl = getIfAlreadyShortened(shortLink);
            if (actualUrl != null) {
                log.info("Shortened URL found: {}", actualUrl);
                RedirectView view = new RedirectView();
                view.setUrl(actualUrl);
                return view;
            } else throw new Exception("Invalid shortened URL link");
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving a shortened URL", ex);
            throw ex;
        }
    }

    private String getIfAlreadyShortened(String id) {
        Optional<Link> optionalLink = linkRepo.findById(id);
        log.info("Finding if short URL exists for {}", id);
        return optionalLink.map(Link::getActualUrl).orElse(null);
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public List<Link> test() {
        List<String> dummyData = Arrays.asList("https://www.youtube.com/watch?v=3ltcxsBiHZ8",
                "https://www.youtube.com/watch?v=IjMESxJdWkg", "https://www.youtube.com/watch?v=Okhd_ijkI6k",
                "https://www.youtube.com/watch?v=aICaAEXDJQQ", "https://www.youtube.com/watch?v=fVeD9vWCpZ8"
        );
        linkRepo.saveAll(dummyData.stream().map(url -> new Link(DigestUtils.md5DigestAsHex(url.getBytes(StandardCharsets.UTF_8)), url)).collect(Collectors.toList()));
        return linkRepo.findAll();
    }

}
