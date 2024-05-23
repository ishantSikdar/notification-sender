package com.notification.sender.service.impl;

import com.notification.sender.dto.MemeDTO;
import com.notification.sender.service.MemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MemeServiceImpl implements MemeService {

    private final WebClient webClient;

    @Autowired
    public MemeServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://meme-api.com").build();
    }

    @Override
    public MemeDTO getMeme() {
        try {
            return this.webClient.get()
                    .uri("/gimme")
                    .retrieve()
                    .bodyToMono(MemeDTO.class)
                    .block();
        } catch (Exception e) {
            log.error("Error calling Meme API " + e.getMessage());
            throw new RuntimeException("Error calling Meme API " + e.getMessage());
        }
    }

}
