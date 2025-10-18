package com.adroitfirm.rydo.geo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.geo.service.impl.RouteServiceImpl;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/geo")
public class RouteController {

	private final RouteServiceImpl routeService;

    @GetMapping("/distance")
    public ResponseEntity<Map<String, Object>> getDistance(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) throws Exception {

        DistanceMatrix matrix = routeService.getDistance(originLat, originLng, destLat, destLng);
        
        if (!matrix.rows[0].elements[0].status.equals(DistanceMatrixElementStatus.OK)) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Distance could not be calculated. Check the coordinates."
            ));
        }

        DistanceMatrixElement element = matrix.rows[0].elements[0];

        if (element.distance == null || element.duration == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Distance or duration is null. Possibly invalid route."
            ));
        }

        double distanceKm = element.distance.inMeters / 1000.0;
        long durationMins = element.duration.inSeconds / 60;

        return ResponseEntity.ok(Map.of(
                "distance_km", distanceKm,
                "duration_min", durationMins,
                "text_distance", element.distance.humanReadable,
                "text_duration", element.duration.humanReadable
        ));
    }

    @GetMapping("/fare")
    public ResponseEntity<Map<String, Object>> calculateFare(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) throws Exception {

        DistanceMatrix matrix = routeService.getDistance(originLat, originLng, destLat, destLng);
        DistanceMatrixElement element = matrix.rows[0].elements[0];
        if (element == null || element.distance == null || !element.status.equals(DistanceMatrixElementStatus.OK)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Distance could not be calculated. Check coordinates."
            ));
        }

        double distanceKm = element.distance.inMeters / 1000.0;
        double fare = routeService.calculateFare(distanceKm);

        return ResponseEntity.ok(Map.of(
                "distance_km", distanceKm,
                "fare", fare
        ));
    }

    @GetMapping("/route")
    public ResponseEntity<Map<String, Object>> getRoute(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) throws Exception {

        DirectionsResult result = routeService.getRoute(originLat, originLng, destLat, destLng);
        
        if (result == null || result.routes == null || result.routes.length == 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "No route found between the given coordinates."
            ));
        }
        
        DirectionsRoute route = result.routes[0];

        return ResponseEntity.ok(Map.of(
                "summary", route.summary,
                "legs", route.legs,
                "overviewPolyline", route.overviewPolyline.getEncodedPath()
        ));
    }

    @GetMapping("/navigation")
    public ResponseEntity<Map<String, Object>> getNavigation(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) throws Exception {

        DirectionsResult result = routeService.getRoute(originLat, originLng, destLat, destLng);
        
        if (result == null || result.routes == null || result.routes.length == 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "No route found between the given coordinates."
            ));
        }
        
        List<String> steps = new ArrayList<>();

        for (DirectionsLeg leg : result.routes[0].legs) {
            for (DirectionsStep step : leg.steps) {
                steps.add(step.htmlInstructions.replaceAll("<[^>]*>", ""));
            }
        }

        return ResponseEntity.ok(Map.of(
                "steps", steps,
                "totalSteps", steps.size()
        ));
    }
}
