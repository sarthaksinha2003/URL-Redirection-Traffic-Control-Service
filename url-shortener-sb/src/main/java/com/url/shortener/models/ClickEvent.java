package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime clickDate;

    //Many clicks → one URL
    @ManyToOne
    @JoinColumn(name = "url_mapping_id")
    private UrlMapping urlMapping; //urlMapping exists so that instead of working with a foreign key ID manually, you can work directly with the related UrlMapping object in Java.
}
