package com.adroitfirm.rydo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.adroitfirm.rydo.dto.VehicleDto;
import com.adroitfirm.rydo.user.entity.Driver;
import com.adroitfirm.rydo.user.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "driver.id", target = "driverId")
    VehicleDto toDto(Vehicle entity);

    @Mapping(source = "driverId", target = "driver", qualifiedByName = "mapDriver")
    Vehicle toEntity(VehicleDto dto);

    @Named("mapDriver")
    default Driver mapDriver(Long id) {
        if (id == null) return null;
        Driver d = new Driver();
        d.setId(id);
        return d;
    }
}