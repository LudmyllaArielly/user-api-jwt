package com.ludmylla.user.api.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLoginDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String passWord;

}
