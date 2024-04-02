package com.starwars.starwars;

import com.starwars.starwars.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.starwars.starwars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/remove_planets.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class StarwarsApplicationTests {
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void createPlanet_ReturnsCreated() {
    ResponseEntity<Planet> sut = restTemplate.postForEntity(
            "/planets",
            PLANET,
            Planet.class);
    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(sut.getBody().getId()).isNotNull();
    assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
  }

  @Test
  @Sql(scripts = {"/import_planets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void getPlanet_ReturnsPlanet() {
    ResponseEntity<Planet> sut = restTemplate
            .getForEntity("/planets/1"
                    , Planet.class);
    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody().getId()).isNotNull();
    assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
  }

  @Test
  @Sql(scripts = {"/import_planets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void listPlanets_ReturnsAllPlanets() {
    ResponseEntity<Planet[]> sut = restTemplate
            .getForEntity("/planets"
                    , Planet[].class);
    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(1);
    assertThat(sut.getBody()[0].getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getBody()[0].getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getBody()[0].getTerrain()).isEqualTo(PLANET.getTerrain());
  }

  @Test
  void listPlanets_ByClimate_ReturnsPlanets() {
  }

  @Test
  void listPlanets_ByTerrain_ReturnsAllPlanets() {
  }

  @Test
  void removePlanet_ReturnsNoContent() {
  }

  @Test
  void contextLoads() {
  }

}
