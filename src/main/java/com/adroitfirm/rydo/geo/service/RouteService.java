package com.adroitfirm.rydo.geo.service;

import java.io.IOException;

import com.adroitfirm.rydo.dto.RideRouteDto;
import com.adroitfirm.rydo.dto.RouteDto;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;

public interface RouteService {

	public DistanceMatrix getDistance(RouteDto routeDto) throws ApiException, InterruptedException, IOException;
    public DirectionsResult getRoute(RouteDto routeDto) throws ApiException, InterruptedException, IOException;
    public double calculateFare(double distanceInKm);
    public RideRouteDto getP2PRoute(RouteDto routeDto) throws ApiException, InterruptedException, IOException;
}
