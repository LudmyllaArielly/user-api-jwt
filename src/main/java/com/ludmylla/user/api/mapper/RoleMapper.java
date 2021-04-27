package com.ludmylla.user.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.user.api.model.Role;
import com.ludmylla.user.api.model.dto.RoleCreateDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	RoleCreateDTO dtoRoleCreateDto(Role source);
	
	@Mapping(target = "id", ignore = true)
	List<RoleCreateDTO> dtoRole(List<Role> source);

}
