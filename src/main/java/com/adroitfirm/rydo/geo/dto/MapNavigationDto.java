package com.adroitfirm.rydo.geo.dto;

import java.util.List;

import com.adroitfirm.rydo.geo.model.Coordinate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MapNavigationDto {
    int totalSteps;
    List<String> steps;
    Coordinate originCoord;
	Coordinate destCoord;
}
