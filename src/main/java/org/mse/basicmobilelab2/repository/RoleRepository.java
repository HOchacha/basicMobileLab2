package org.mse.basicmobilelab2.repository;


import org.mse.basicmobilelab2.entity.ERole;
import org.mse.basicmobilelab2.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
