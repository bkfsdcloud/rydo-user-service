package com.adroitfirm.rydo.geo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MapUtil {

	public List<Map<String, Double>> decodePolyline(String encoded) {
	    List<Map<String, Double>> poly = new ArrayList<>();
	    int index = 0, lat = 0, lng = 0;

	    while (index < encoded.length()) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;

	        poly.add(Map.of("latitude", lat / 1e5, "longitude", lng / 1e5));
	    }
	    return poly;
	}

}
