package com.adroitfirm.rydo.user.mapper;

import com.adroitfirm.rydo.user.dto.DriverKycDto;
import com.adroitfirm.rydo.user.entity.DriverKyc;
import com.adroitfirm.rydo.user.entity.Driver;
import com.adroitfirm.rydo.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DriverKycMapper {

    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "verifiedBy.id", target = "verifiedBy")
    DriverKycDto toDto(DriverKyc entity);

    @Mapping(source = "driverId", target = "driver", qualifiedByName = "mapDriverId")
    @Mapping(source = "verifiedBy", target = "verifiedBy", qualifiedByName = "mapUserId")
    DriverKyc toEntity(DriverKycDto dto);

    @Named("mapDriverId")
    default Driver mapDriverId(Long id) {
        if (id == null) return null;
        Driver d = new Driver();
        d.setId(id);
        return d;
    }

    @Named("mapUserId")
    default User mapUserId(Long id) {
        if (id == null) return null;
        User u = new User();
        u.setId(id);
        return u;
    }
}