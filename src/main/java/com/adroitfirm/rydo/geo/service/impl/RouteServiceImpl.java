package com.adroitfirm.rydo.geo.service.impl;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.geo.dto.RideRouteDto;
import com.adroitfirm.rydo.geo.dto.RouteDto;
import com.adroitfirm.rydo.geo.exception.InvalidRouteRequestException;
import com.adroitfirm.rydo.geo.model.Coordinate;
import com.adroitfirm.rydo.geo.model.PlaceCoordinate;
import com.adroitfirm.rydo.geo.service.RouteService;
import com.adroitfirm.rydo.geo.util.MapUtil;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    private final GeoApiContext geoApiContext;
    private final MapUtil mapUtil;
    private final RedisCacheServiceImpl cacheServiceImpl;
    private final MapServiceClient mapServiceClient;
    
    public RouteServiceImpl(@Value("${google.maps.api-key}") String apiKey, MapUtil mapUtil, 
    		RedisCacheServiceImpl cacheServiceImpl, MapServiceClient mapServiceClient) {
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        this.mapUtil = mapUtil;
        this.cacheServiceImpl = cacheServiceImpl;
        this.mapServiceClient = mapServiceClient;
    }

    public DistanceMatrix getDistance(RouteDto routeDto) throws ApiException, InterruptedException, IOException {
    	DistanceMatrixApiRequest matrixApiRequest = DistanceMatrixApi.newRequest(geoApiContext);
    	if (Objects.nonNull(routeDto.getOriginAddress())) {
    		return matrixApiRequest.origins(routeDto.getOriginAddress())
    				.destinations(routeDto.getDestAddress()).mode(TravelMode.DRIVING)
    				.await();
    	}
        return matrixApiRequest
                .origins(new LatLng(routeDto.getOriginCoord().getLat(), routeDto.getOriginCoord().getLng()))
                .destinations(new LatLng(routeDto.getDestCoord().getLat(), routeDto.getDestCoord().getLng()))
                .mode(TravelMode.DRIVING)
                .await();
    }

    public DirectionsResult getRoute(RouteDto routeDto) throws ApiException, InterruptedException, IOException {
    	DirectionsApiRequest directionsApiRequest = DirectionsApi.newRequest(geoApiContext);
    	if (Objects.nonNull(routeDto.getOriginPlaceId())) {
    		return directionsApiRequest.originPlaceId(routeDto.getOriginPlaceId())
    				.destinationPlaceId(routeDto.getDestPlaceId())
    				.mode(TravelMode.DRIVING)
    				.await();
    	}
        return directionsApiRequest
                .origin(new LatLng(routeDto.getOriginCoord().getLat(), routeDto.getOriginCoord().getLng()))
                .destination(new LatLng(routeDto.getDestCoord().getLat(), routeDto.getDestCoord().getLng()))
                .mode(TravelMode.DRIVING)
                .await();
    }

    public double calculateFare(double distanceInKm) {
        double baseFare = 50;        // base fare
        double perKmRate = 12;       // ₹12 per km
        return baseFare + (distanceInKm * perKmRate);
    }

	@Override
	public RideRouteDto getP2PRoute(RouteDto routeDto) throws ApiException, InterruptedException, IOException {
		PlaceCoordinate cachedOriginCoordinate = cacheServiceImpl.getPlace(routeDto.getOriginPlaceId());
		PlaceCoordinate cachedDestinationCoordinate = cacheServiceImpl.getPlace(routeDto.getDestPlaceId());
		
		if (Objects.nonNull(cachedOriginCoordinate) && Objects.nonNull(cachedDestinationCoordinate)) {
			log.info("Route retrieved from cache: [{}] : [{}]", cachedOriginCoordinate.getPlaceId(),
					cachedDestinationCoordinate.getPlaceId());
			RideRouteDto rideRouteDto = cacheServiceImpl.getRoute(cachedOriginCoordinate, cachedDestinationCoordinate);
			
			if (Objects.nonNull(rideRouteDto)) {
				return rideRouteDto;
			}
		}
		if (Objects.isNull(cachedOriginCoordinate) || Objects.isNull(cachedDestinationCoordinate)) {
			if (Objects.isNull(cachedOriginCoordinate)) {
				cachedOriginCoordinate = mapServiceClient.getLatLngByPlaceId(routeDto.getOriginPlaceId());
				this.cacheServiceImpl.cachePlace(cachedOriginCoordinate);
				log.info("Plcae coordinates cached : [{}]", cachedOriginCoordinate.getPlaceId());
			}
			if (Objects.isNull(cachedDestinationCoordinate)) {
				cachedDestinationCoordinate = mapServiceClient.getLatLngByPlaceId(routeDto.getDestPlaceId());
				this.cacheServiceImpl.cachePlace(cachedDestinationCoordinate);
				log.info("Plcae coordinates cached : [{}]", cachedDestinationCoordinate.getPlaceId());
			}
		}
		
        DirectionsResult direction = getRoute(routeDto);
        
        if (direction == null || direction.routes == null || direction.routes.length == 0) {
        	throw new InvalidRouteRequestException("No route found between the given coordinates.");
        }
        DirectionsRoute route = direction.routes[0];

        routeDto.setOriginAddress(route.legs[0].startAddress);
        routeDto.setDestAddress(route.legs[0].endAddress);
        DistanceMatrix matrix = getDistance(routeDto);
        
        if (!matrix.rows[0].elements[0].status.equals(DistanceMatrixElementStatus.OK)) {
            throw new InvalidRouteRequestException("Distance could not be calculated. Check the coordinates.");
        }
        DistanceMatrixElement element = matrix.rows[0].elements[0];

        if (element.distance == null || element.duration == null) {
        	throw new InvalidRouteRequestException("Distance or duration is null. Possibly invalid route");
        }
        
        LatLng originLatLng = route.legs[0].startLocation;
        LatLng destLatLng = route.legs[0].endLocation;
        
        double distanceKm = element.distance.inMeters / 1000.0;
        long durationMins = element.duration.inSeconds / 60;
        double fare = calculateFare(distanceKm);
        
        RideRouteDto rideRouteDto = RideRouteDto.builder().summary(route.summary)
        		.originCoord(Coordinate.builder().lat(originLatLng.lat).lng(originLatLng.lng).build())
        		.destCoord(Coordinate.builder().lat(destLatLng.lat).lng(destLatLng.lng).build())
        		.coordinates(mapUtil.decodePolyline(route.overviewPolyline.getEncodedPath()))
        		.distanceKm(distanceKm).fare(fare)
        		.distanceTxt(element.distance.humanReadable)
        		.durationMins(durationMins)
        		.durationTxt(element.duration.humanReadable)
        		.build();
        
        cacheServiceImpl.cacheRoute(cachedOriginCoordinate, cachedDestinationCoordinate, rideRouteDto);
        log.info("Caching the route for the points: [{}] : [{}]", cachedOriginCoordinate.getPlaceId(),
        		cachedDestinationCoordinate.getPlaceId());
        
		return rideRouteDto;
	}
}
