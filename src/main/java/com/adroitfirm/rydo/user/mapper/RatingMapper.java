package com.adroitfirm.rydo.user.mapper;

import com.adroitfirm.rydo.user.dto.RatingDto;
import com.adroitfirm.rydo.user.entity.Rating;
import com.adroitfirm.rydo.user.entity.Ride;
import com.adroitfirm.rydo.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "ride.id", target = "rideId")
    @Mapping(source = "ratedBy.id", target = "ratedBy")
    @Mapping(source = "ratedFor.id", target = "ratedFor")
    RatingDto toDto(Rating entity);

    @Mapping(source = "rideId", target = "ride", qualifiedByName = "mapRide")
    @Mapping(source = "ratedBy", target = "ratedBy", qualifiedByName = "mapUser")
    @Mapping(source = "ratedFor", target = "ratedFor", qualifiedByName = "mapUser")
    Rating toEntity(RatingDto dto);

    @Named("mapRide")
    default Ride mapRide(Long id) {
        if (id == null) return null;
        Ride r = new Ride();
        r.setId(id);
        return r;
    }

    @Named("mapUser")
    default User mapUser(Long id) {
        if (id == null) return null;
        User u = new User();
        u.setId(id);
        return u;
    }
}