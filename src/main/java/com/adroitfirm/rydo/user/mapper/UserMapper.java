package com.adroitfirm.rydo.user.mapper;

import org.mapstruct.Mapper;

import com.adroitfirm.rydo.dto.UserDto;
import com.adroitfirm.rydo.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);
    User toEntity(UserDto dto);
}