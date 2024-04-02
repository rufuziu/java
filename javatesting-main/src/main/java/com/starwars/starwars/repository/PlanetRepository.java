package com.starwars.starwars.repository;

import com.starwars.starwars.domain.Planet;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet,Long> {
  Optional<Planet> findByName(String name);
  @Query("SELECT p FROM planets p WHERE " +
          "(:climate is null or p.climate = :climate) " +
          "and (:terrain is null"
          + " or p.terrain = :terrain)")
  List<Planet> findByClimateAndTerrain(String climate,
                                       String terrain);
}
