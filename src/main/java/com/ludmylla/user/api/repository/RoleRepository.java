package com.ludmylla.user.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ludmylla.user.api.model.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByName(String name);

}
