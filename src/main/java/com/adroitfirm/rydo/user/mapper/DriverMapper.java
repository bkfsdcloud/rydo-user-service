package com.adroitfirm.rydo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.adroitfirm.rydo.dto.DriverDto;
import com.adroitfirm.rydo.user.entity.Driver;
import com.adroitfirm.rydo.user.entity.User;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(source = "user.id", target = "userId")
    DriverDto toDto(Driver entity);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUserIdToUser")
    Driver toEntity(DriverDto dto);

    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        User u = new User();
        u.setId(userId);
        return u;
    }
}