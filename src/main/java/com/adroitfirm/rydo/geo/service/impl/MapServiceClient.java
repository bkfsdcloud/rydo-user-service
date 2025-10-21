package com.adroitfirm.rydo.geo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import com.adroitfirm.rydo.geo.dto.GeocodeResponse;
import com.adroitfirm.rydo.geo.dto.PredictionResponse;
import com.adroitfirm.rydo.geo.model.Coordinate;
import com.adroitfirm.rydo.geo.model.PlaceCoordinate;
import com.adroitfirm.rydo.geo.model.PlaceSuggesstion;
import com.adroitfirm.rydo.geo.service.MapPlaceService;
import com.adroitfirm.rydo.geo.service.RedisCacheService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MapServiceClient implements MapPlaceService {

	@Value("${google.maps.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final RedisCacheService redisCacheService;
    
    public MapServiceClient(RestTemplate restTemplate, RedisCacheService redisCacheService) {
		this.restTemplate = restTemplate;
		this.redisCacheService = redisCacheService;
	}

    @Override
	public List<PlaceCoordinate> getLatLngByAddress(String address) {
        String url = UriComponentsBuilder
                .fromUriString("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("address", address)
                .queryParam("key", apiKey)
                .toUriString();

        GeocodeResponse response = restTemplate.getForObject(url, GeocodeResponse.class);

        if (response == null || !"OK".equals(response.getStatus())) {
            throw new RuntimeException("Failed to fetch location for address: " + address);
        }

        List<PlaceCoordinate> resultset = response.getResults().stream()
        	.map(result -> {
        		return PlaceCoordinate.builder()
        				.address(result.getAddress_components().get(0).getLong_name())
        				.placeId(result.getPlace_id())
        				.cooridinate(Coordinate.builder()
    						.lat(result.getGeometry().getLocation().getLat())
    						.lng(result.getGeometry().getLocation().getLng()).build())
        				.build();
        	}).collect(Collectors.toList());
        
        return resultset;
    }
	
	@Override
	public PlaceCoordinate getLatLngByPlaceId(String placeId) {
        String url = UriComponentsBuilder
                .fromUriString("https://maps.googleapis.com/maps/api/place/details/json")
                .queryParam("place_id", placeId)
                .queryParam("fields", "geometry")
                .queryParam("key", apiKey)
                .toUriString();

        GeocodeResponse response = restTemplate.getForObject(url, GeocodeResponse.class);

        if (response == null || !"OK".equals(response.getStatus())) {
            throw new RuntimeException("Failed to fetch coordinate for placeId: " + placeId);
        }
        
        if (Objects.nonNull(response.getResult())) {
        	GeocodeResponse.Result result = response.getResult();
        	return PlaceCoordinate.builder()
        			.placeId(placeId)
        			.cooridinate(Coordinate.builder()
        					.lat(result.getGeometry().getLocation().getLat())
        					.lng(result.getGeometry().getLocation().getLng()).build())
        			.build();
        }
        throw new RuntimeException("No Matching place for placeId: " + placeId);
    }
	
	@Override
	public List<PlaceSuggesstion> getAutocomplete(String input, String sessionToken, Coordinate cooridinate) {
		List<PlaceSuggesstion> cachedSuggestion = redisCacheService.getSuggesstion(input);
		if (!CollectionUtils.isEmpty(cachedSuggestion)) {
			log.info("Retrieved suggestion for input [{}]from cache", input);
			return cachedSuggestion;
		}
		UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromUriString("https://maps.googleapis.com/maps/api/place/autocomplete/json")
                .queryParam("input", UriUtils.encode(input, StandardCharsets.UTF_8))
                .queryParam("key", apiKey);

		if (Objects.nonNull(sessionToken) && !sessionToken.isEmpty()) {
			urlBuilder.queryParam("sessiontoken", sessionToken);
		}
        if (Objects.nonNull(cooridinate)) {
        	urlBuilder.queryParam("location", cooridinate.getLat() + "," + cooridinate.getLng());
        	urlBuilder.queryParam("radius", 20000);
        }
        urlBuilder.queryParam("components", "country:IN");

        ResponseEntity<PredictionResponse> predictions = restTemplate.exchange(urlBuilder.build().toUri(), HttpMethod.GET
        		, null, new ParameterizedTypeReference<PredictionResponse>() {});
        
        if (!predictions.getStatusCode().is2xxSuccessful()) {
        	throw new RuntimeException("Failed to fetch autocomplete suggestion for input: " + input);
        }
        
        cachedSuggestion = predictions.getBody().getPredictions();
        if (!CollectionUtils.isEmpty(cachedSuggestion)) {
        	redisCacheService.cacheSuggesstion(input, cachedSuggestion);
        	log.info("Caching suggestion for input : [{}]", input);
        }

        return cachedSuggestion;
    }
}
