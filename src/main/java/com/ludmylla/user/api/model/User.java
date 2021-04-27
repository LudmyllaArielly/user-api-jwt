package com.ludmylla.user.api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = { "email" })})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String cpf;
	private LocalDate dateOfBirth;
	private String email;
	private String password;
	
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = 
	@JoinColumn(name="user_id", referencedColumnName = "id"),inverseJoinColumns = 
	@JoinColumn(name ="role_id", referencedColumnName = "id"))
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles == null ? null : new ArrayList<>(roles);
	}

	public void setRoles(List<Role> roles) {
		if (roles == null) {
			this.roles = null;
		} else {
			this.roles = Collections.unmodifiableList(roles);
		}
	}

}
