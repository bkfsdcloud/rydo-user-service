package com.adroitfirm.rydo.geo.service;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;

public interface RouteService {

	public DistanceMatrix getDistance(double originLat, double originLng, double destLat, double destLng) throws Exception;

    public DirectionsResult getRoute(double originLat, double originLng, double destLat, double destLng) throws Exception;
}
