package org.mse.basicmobilelab2.repository;



import org.mse.basicmobilelab2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRolename(String roleName);
}
