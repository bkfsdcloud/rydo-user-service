package com.adroitfirm.rydo.geo.dto;

import java.util.List;
import java.util.Map;

import com.adroitfirm.rydo.geo.model.Coordinate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.maps.model.DirectionsLeg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class RideRouteDto {

	String summary;
    Coordinate originCoord;
	Coordinate destCoord;
    List<Map<String, Double>> coordinates;
    double distanceKm;
    long durationMins;
    String distanceTxt;
    String durationTxt;
    double fare;
}
