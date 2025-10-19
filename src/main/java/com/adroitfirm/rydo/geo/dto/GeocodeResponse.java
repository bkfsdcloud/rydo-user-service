package com.adroitfirm.rydo.geo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class GeocodeResponse {

    private List<Result> results;
    private Result result;
    private String status;

    @Data
    public static class Result {
        private List<AddressComponent> address_components;
        private String formatted_address;
        private Geometry geometry;
        private String place_id;
        private List<String> types;
        private PlusCode plus_code;   // optional field

    }

    @Data
    public static class AddressComponent {
        private String long_name;
        private String short_name;
        private List<String> types;
    }

    @Data
    public static class Geometry {
        private Location location;
        private String location_type;
        private Viewport viewport;

    }

    @Data
    public static class Location {
        private double lat;
        private double lng;

    }

    @Data
    public static class Viewport {
        private Location northeast;
        private Location southwest;

    }

    @Data
    public static class PlusCode {
        private String compound_code;
        private String global_code;
    }
}

