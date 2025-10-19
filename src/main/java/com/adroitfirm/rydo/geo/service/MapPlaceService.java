package com.adroitfirm.rydo.geo.service;

import java.util.List;

import com.adroitfirm.rydo.geo.model.Coordinate;
import com.adroitfirm.rydo.geo.model.PlaceCoordinate;
import com.adroitfirm.rydo.geo.model.PlaceSuggesstion;

public interface MapPlaceService {

	public List<PlaceCoordinate> getLatLngByAddress(String address);
	public PlaceCoordinate getLatLngByPlaceId(String placeId);
	public List<PlaceSuggesstion> getAutocomplete(String input, String sessionToken, Coordinate cooridinate);
}
