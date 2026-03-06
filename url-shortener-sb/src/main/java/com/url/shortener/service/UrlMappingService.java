package com.url.shortener.service;

import com.url.shortener.dtos.ClickEventDTO;
import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.UrlMappingRepository;
import com.url.shortener.utils.Base62Encoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private UrlMappingRepository urlMappingRepository;
    private ClickEventRepository clickEventRepository;
    public UrlMappingDTO creatShortUrl(String originalUrl, User user) {
        // STEP 1: Save to DB first with placeholder to get the auto-generated ID
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl("PENDING"); // temporary value
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);

        // STEP 2: Use the DB ID to generate a guaranteed unique Base62 short URL
        // e.g., ID 1 → "1", ID 62 → "Z", ID 10042 → "2bQ"
        String shortUrl = Base62Encoder.encode(savedUrlMapping.getId());

        // STEP 3: Collision detection — while loop keeps trying if collision found
        // In practice this almost NEVER runs because DB ID is always unique
        long offset = 0;
        while (urlMappingRepository.existsByShortUrl(shortUrl)) {
            offset++;
            // Shift the ID far away to get a completely different Base62 string
            shortUrl = Base62Encoder.encode(savedUrlMapping.getId() + offset * 1_000_000_000L);
        }

        // STEP 4: Update the record with the real short URL and save
        savedUrlMapping.setShortUrl(shortUrl);
        UrlMapping finalMapping = urlMappingRepository.save(savedUrlMapping);
        return convertToDTO(finalMapping);
    }

    private UrlMappingDTO convertToDTO(UrlMapping urlMapping){
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        return urlMappingDTO;
    }

//    private String generateShortUrl() {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuilder shortUrl = new StringBuilder(8);
//        for (int i = 0; i < 8; i++) {
//            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return shortUrl.toString();
//    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream().map(this::convertToDTO).toList();
    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping != null){
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping,start,end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(),Collectors.counting()))
                    .entrySet().stream()
                    .map(entry->{
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),
                end.plusDays(1).atStartOfDay());
        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(),Collectors.counting()));
    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping != null){
            urlMapping.setClickCount(urlMapping.getClickCount()+1);
            urlMappingRepository.save(urlMapping);
            // Record click event
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepository.save(clickEvent);
        }
        return urlMapping;
    }
}
