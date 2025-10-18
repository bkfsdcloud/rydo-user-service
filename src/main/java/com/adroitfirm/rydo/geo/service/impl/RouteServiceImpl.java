package com.adroitfirm.rydo.geo.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.geo.service.RouteService;
import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;


@Service
public class RouteServiceImpl implements RouteService {

    private final GeoApiContext geoApiContext;

    public RouteServiceImpl(@Value("${google.maps.api-key}") String apiKey) {
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public DistanceMatrix getDistance(double originLat, double originLng, double destLat, double destLng) throws Exception {
        return DistanceMatrixApi.newRequest(geoApiContext)
                .origins(new LatLng(originLat, originLng))
                .destinations(new LatLng(destLat, destLng))
                .mode(TravelMode.DRIVING)
                .await();
    }

    public DirectionsResult getRoute(double originLat, double originLng, double destLat, double destLng) throws Exception {
        return DirectionsApi.newRequest(geoApiContext)
                .origin(new LatLng(originLat, originLng))
                .destination(new LatLng(destLat, destLng))
                .mode(TravelMode.DRIVING)
                .await();
    }

    public double calculateFare(double distanceInKm) {
        double baseFare = 50;        // base fare
        double perKmRate = 12;       // ₹12 per km
        return baseFare + (distanceInKm * perKmRate);
    }
}
