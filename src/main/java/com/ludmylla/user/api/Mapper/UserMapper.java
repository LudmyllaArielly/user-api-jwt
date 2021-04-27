package com.ludmylla.user.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ludmylla.user.api.model.User;
import com.ludmylla.user.api.model.dto.UserCreateDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User toUser (UserCreateDTO source);
	

}
