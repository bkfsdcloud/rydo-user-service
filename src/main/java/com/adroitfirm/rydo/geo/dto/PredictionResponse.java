package com.adroitfirm.rydo.geo.dto;

import java.util.List;

import com.adroitfirm.rydo.geo.model.PlaceSuggesstion;

import lombok.Data;

@Data
public class PredictionResponse {

	private String status;
	private List<PlaceSuggesstion> predictions;
}
