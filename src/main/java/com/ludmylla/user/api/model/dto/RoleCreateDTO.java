package com.ludmylla.user.api.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleCreateDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;

}
