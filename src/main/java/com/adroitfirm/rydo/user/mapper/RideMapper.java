package com.adroitfirm.rydo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.adroitfirm.rydo.dto.RideDto;
import com.adroitfirm.rydo.user.entity.Driver;
import com.adroitfirm.rydo.user.entity.Ride;
import com.adroitfirm.rydo.user.entity.User;

@Mapper(componentModel = "spring")
public interface RideMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "driver.id", target = "driverId")
    RideDto toDto(Ride entity);

    @Mapping(source = "customerId", target = "customer", qualifiedByName = "mapUser")
    @Mapping(source = "driverId", target = "driver", qualifiedByName = "mapDriver")
    Ride toEntity(RideDto dto);

    @Named("mapUser")
    default User mapUser(Long id) {
        if (id == null) return null;
        User u = new User();
        u.setId(id);
        return u;
    }

    @Named("mapDriver")
    default Driver mapDriver(Long id) {
        if (id == null) return null;
        Driver d = new Driver();
        d.setId(id);
        return d;
    }
}