package com.starwars.starwars.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity(name = "planets")
public class Planet {
  public Planet() {
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  @NotEmpty
  private String name;
  @Column(nullable = false)
  @NotEmpty
  private String climate;
  @Column(nullable = false)
  @NotEmpty
  private String terrain;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClimate() {
    return climate;
  }

  public void setClimate(String climate) {
    this.climate = climate;
  }

  public String getTerrain() {
    return terrain;
  }

  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }

  public Planet(String name, String climate, String terrain) {
    this.name = name;
    this.climate = climate;
    this.terrain = terrain;
  }

  public Planet(String climate, String terrain){
    this.climate = climate;
    this.terrain = terrain;
  }
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(obj,this);
  }
}
