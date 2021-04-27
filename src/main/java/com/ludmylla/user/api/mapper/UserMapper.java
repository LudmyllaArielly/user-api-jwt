package com.ludmylla.user.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.model.dto.UserCreateDTO;
import com.ludmylla.user.api.model.dto.UserLoginDTO;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", source = "roleCreateDTOs")
	User toUser(UserCreateDTO source);	

	@Mappings({
			@Mapping(target = "id", ignore=true),
			@Mapping(target = "firstName", ignore=true),
			@Mapping(target = "lastName", ignore=true),
			@Mapping(target = "cpf", ignore=true),
			@Mapping(target = "dateOfBirth", ignore=true),
			@Mapping(target = "roles", ignore=true),
	})
	User toUser (UserLoginDTO source);
	
}
