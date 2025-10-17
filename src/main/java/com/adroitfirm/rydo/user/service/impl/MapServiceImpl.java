package com.adroitfirm.rydo.user.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.adroitfirm.rydo.user.dto.GMapDto;
import com.adroitfirm.rydo.user.service.MapService;

import reactor.core.publisher.Mono;

@Service
public class MapServiceImpl implements MapService {
	
	@Value("${google.api.key}")
    private String apiKey;

    private final WebClient webClient;
    
	public MapServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Mono<String> getRoute(GMapDto gMapDto) {
		String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%f,%f&destination=%f,%f&key=%s",
                gMapDto.getOriginLat(), gMapDto.getOriginLng(), gMapDto.getDestLat(), gMapDto.getDestLng(), apiKey
        );

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
	}

}
