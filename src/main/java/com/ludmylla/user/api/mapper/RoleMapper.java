package com.ludmylla.user.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.user.api.model.Role;
import com.ludmylla.user.api.model.dto.RoleCreateDTO;

@Mapper
public interface RoleMapper {
	
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
	
	@Mapping(target = "id", ignore =true)
	Role toRole (RoleCreateDTO source);
}
