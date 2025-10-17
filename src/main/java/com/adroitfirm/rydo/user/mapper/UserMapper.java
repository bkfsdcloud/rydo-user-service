package com.adroitfirm.rydo.user.mapper;

import com.adroitfirm.rydo.user.dto.UserDto;
import com.adroitfirm.rydo.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);
    User toEntity(UserDto dto);
}