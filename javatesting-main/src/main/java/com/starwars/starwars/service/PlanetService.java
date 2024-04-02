package com.starwars.starwars.service;

import com.starwars.starwars.domain.Planet;
import com.starwars.starwars.querybuilder.QueryBuilder;
import com.starwars.starwars.repository.PlanetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
  private final PlanetRepository repository;

  public PlanetService(PlanetRepository repository) {
    this.repository = repository;
  }

  public Planet create(Planet planet){
    return repository.save(planet);
  }

  public Optional<Planet> findById(Long id){
    return repository.findById(id);
  }

  public List<Planet> planetList(
          String climate,
          String terrain){
//    Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate,terrain));
//    return repository.findAll(query);
  return repository.findByClimateAndTerrain(climate,terrain);
  }

  public Optional<Planet> findByName(String name){
    return repository.findByName(name);
  }

  public void remove(Long id){
    Optional<Planet> planet = findById(id);
    if(planet.isPresent()) repository.delete(planet.get());
    else throw new EntityNotFoundException();
  }
}
