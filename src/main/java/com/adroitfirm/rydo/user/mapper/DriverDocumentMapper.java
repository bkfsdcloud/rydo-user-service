package com.adroitfirm.rydo.user.mapper;

import com.adroitfirm.rydo.user.dto.DriverDocumentDto;
import com.adroitfirm.rydo.user.entity.DriverDocument;
import com.adroitfirm.rydo.user.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DriverDocumentMapper {

    @Mapping(source = "driver.id", target = "driverId")
    DriverDocumentDto toDto(DriverDocument entity);

    @Mapping(source = "driverId", target = "driver", qualifiedByName = "mapDriver")
    DriverDocument toEntity(DriverDocumentDto dto);

    @Named("mapDriver")
    default Driver mapDriver(Long id) {
        if (id == null) return null;
        Driver d = new Driver();
        d.setId(id);
        return d;
    }
}