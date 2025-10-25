package com.adroitfirm.rydo.geo.service.impl;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.dto.RideRouteDto;
import com.adroitfirm.rydo.geo.service.RedisCacheService;
import com.adroitfirm.rydo.model.PlaceCoordinate;
import com.adroitfirm.rydo.model.PlaceSuggesstion;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

	private final RedisTemplate<String, Object> redisTemplate;

//    public void cacheDriverLocation(String driverId, DriverLocation location) {
//        redisTemplate.opsForValue().set("driver:" + driverId, location, Duration.ofSeconds(30));
//    }

//    public DriverLocation getDriverLocation(String driverId) {
//        return (DriverLocation) redisTemplate.opsForValue().get("driver:" + driverId);
//    }

    public void cacheRoute(PlaceCoordinate origin, PlaceCoordinate destination, RideRouteDto rideRouteDto) {
    	String key = String.format("map:route:%f,%f:%f,%f", origin.getCooridinate().getLat(), 
    			origin.getCooridinate().getLng(), destination.getCooridinate().getLat(), 
    			destination.getCooridinate().getLng());
        redisTemplate.opsForValue().set(key, rideRouteDto, Duration.ofMinutes(5));
    }

    public RideRouteDto getRoute(PlaceCoordinate origin, PlaceCoordinate destination) {
    	String key = String.format("map:route:%f,%f:%f,%f", origin.getCooridinate().getLat(), 
    			origin.getCooridinate().getLng(), destination.getCooridinate().getLat(), 
    			destination.getCooridinate().getLng());
        return (RideRouteDto) redisTemplate.opsForValue().get(key);
    }

	@Override
	public void cachePlace(PlaceCoordinate placeCoordinate) {
		redisTemplate.opsForValue().set("map:coord:" + placeCoordinate.getPlaceId(), placeCoordinate, Duration.ofHours(24));
	}

	@Override
	public PlaceCoordinate getPlace(String place) {
		return (PlaceCoordinate) redisTemplate.opsForValue().get("map:coord:" + place);
	}

	@Override
	public void cacheSuggesstion(String searchFor, List<PlaceSuggesstion> placeSuggesstions) {
		redisTemplate.opsForValue().set("map:suggestion:" + searchFor, placeSuggesstions);		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PlaceSuggesstion> getSuggesstion(String searchFor) {
		return (List<PlaceSuggesstion>) redisTemplate.opsForValue().get("map:suggestion:" + searchFor);
	}
}
