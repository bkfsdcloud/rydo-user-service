package com.adroitfirm.rydo.geo.service;

import java.util.List;

import com.adroitfirm.rydo.dto.RideRouteDto;
import com.adroitfirm.rydo.model.PlaceCoordinate;
import com.adroitfirm.rydo.model.PlaceSuggesstion;


public interface RedisCacheService {

	public void cacheRoute(PlaceCoordinate origin, PlaceCoordinate destination, RideRouteDto rideRouteDto);
    public RideRouteDto getRoute(PlaceCoordinate origin, PlaceCoordinate destination);
    public void cachePlace(PlaceCoordinate placeCoordinate);
    public PlaceCoordinate getPlace(String place);
    public void cacheSuggesstion(String searchFor, List<PlaceSuggesstion> placeSuggesstions);
    public List<PlaceSuggesstion> getSuggesstion(String searchFor);
}
