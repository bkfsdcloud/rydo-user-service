package com.adroitfirm.rydo.geo.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.maps.model.DirectionsLeg;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MapRouteDto {
    String summary;
    DirectionsLeg[] legs;
    List<Map<String, Double>> coordinates;
}
