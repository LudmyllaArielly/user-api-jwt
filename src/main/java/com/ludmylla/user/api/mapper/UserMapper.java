package com.ludmylla.user.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.model.dto.UserCreateDTO;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", source = "roleCreateDTOs")
	User toUser(UserCreateDTO source);

}
