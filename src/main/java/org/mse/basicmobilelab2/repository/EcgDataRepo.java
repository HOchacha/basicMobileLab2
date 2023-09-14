package org.mse.basicmobilelab2.repository;

import org.mse.basicmobilelab2.entity.EcgData;
import org.mse.basicmobilelab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EcgDataRepo extends JpaRepository<EcgData, Long> {
    List<EcgData> findByUser(User user);
}
