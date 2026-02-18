package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount;
    private LocalDateTime createdDate;

    //One user can create many short URLs
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //One short URL can have many clicks
    @OneToMany(mappedBy = "urlMapping")
    private List<ClickEvent> clickEvents;
}
