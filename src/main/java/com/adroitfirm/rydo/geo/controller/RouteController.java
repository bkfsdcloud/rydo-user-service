package com.adroitfirm.rydo.geo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.geo.dto.MapNavigationDto;
import com.adroitfirm.rydo.geo.dto.RideRouteDto;
import com.adroitfirm.rydo.geo.dto.RouteDto;
import com.adroitfirm.rydo.geo.model.Coordinate;
import com.adroitfirm.rydo.geo.model.PlaceSuggesstion;
import com.adroitfirm.rydo.geo.service.impl.MapServiceClient;
import com.adroitfirm.rydo.geo.service.impl.RouteServiceImpl;
import com.adroitfirm.rydo.user.util.ApiResponse;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/geo")
public class RouteController {

	private final RouteServiceImpl routeService;
	private final MapServiceClient mapPlaceServiceImpl;

    @GetMapping("/route")
    public ResponseEntity<ApiResponse<RideRouteDto>> getRoute(RouteDto routeDto) throws Exception {
    	RideRouteDto rideRouteDto = routeService.getP2PRoute(routeDto);
        return ResponseEntity.ok(ApiResponse.success(rideRouteDto, "Route plan generated"));
    }

    @GetMapping("/navigation")
    public ResponseEntity<ApiResponse<MapNavigationDto>> getNavigation(RouteDto routeDto) throws Exception {

        DirectionsResult result = routeService.getRoute(routeDto);
        
        if (result == null || result.routes == null || result.routes.length == 0) {
            return ResponseEntity.badRequest().body(ApiResponse.error("No route found between the given coordinates."));
        }
        
        List<String> steps = new ArrayList<>();
        
        DirectionsRoute route = result.routes[0];
        
        LatLng originLatLng = route.legs[0].startLocation;
        LatLng destLatLng = route.legs[0].endLocation;

        for (DirectionsLeg leg : result.routes[0].legs) {
            for (DirectionsStep step : leg.steps) {
                steps.add(step.htmlInstructions.replaceAll("<[^>]*>", ""));
            }
        }
        MapNavigationDto mapNavigationDto = MapNavigationDto.builder().steps(steps).totalSteps(steps.size())
        		.originCoord(Coordinate.builder().lat(originLatLng.lat).lng(originLatLng.lng).build())
        		.destCoord(Coordinate.builder().lat(destLatLng.lat).lng(destLatLng.lng).build())
        		.build();
        return ResponseEntity.ok(ApiResponse.success(mapNavigationDto, "Completed"));
    }
    
    @GetMapping("/autocomplete")
    public ResponseEntity<List<PlaceSuggesstion>> autocomplete(
            @RequestParam String input,
            @RequestParam String sessionToken,
            @RequestParam(required = false) Coordinate cooridinate
    ) {
        return ResponseEntity.ok(mapPlaceServiceImpl.getAutocomplete(input, sessionToken, cooridinate));
    }
}
