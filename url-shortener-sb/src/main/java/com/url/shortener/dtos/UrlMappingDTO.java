package com.url.shortener.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDTO {
    private Long id;
    private String shortUrl;
    private String originalUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    private String username;
}
