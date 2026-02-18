package com.url.shortener.controller;

import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.User;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;

    @PostMapping("/shorten")
    @PostAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> createShortenUrl(@RequestBody Map<String, String> request,
                                                          Principal principal){
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUserName(principal.getName());
        UrlMappingDTO urlMappingDTO  = urlMappingService.creatShortUrl(originalUrl,user);
        return ResponseEntity.ok(urlMappingDTO);
    }
}
