package com.starwars.starwars.controller;

import com.starwars.starwars.domain.Planet;
import com.starwars.starwars.service.PlanetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {
  private final PlanetService service;

  public PlanetController(PlanetService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Planet> create(@Valid @RequestBody
                                       Planet planet) {
    Planet planetCreated = service.create(planet);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(planetCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Planet> get(@PathVariable("id") Long id) {
    return service.findById(id)
            .map(pl -> ResponseEntity.ok(pl))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Planet> get(@PathVariable("name") String name) {
    return service.findByName(name)
            .map(pl -> ResponseEntity.ok(pl))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Planet>> get(
          @RequestParam(required = false, name = "climate") String climate,
          @RequestParam(required = false, name = "terrain") String terrain
  ) {
    return ResponseEntity.ok(service.planetList(climate, terrain));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }
}
