package com.ludmylla.user.api.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class UserCreateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String cpf;
	private LocalDate dateOfBirth;

	private String email;
	private String password;
	
	private List<RoleCreateDTO> roleCreateDTOs;

}
