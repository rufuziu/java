package com.starwars.starwars.repository;

import com.starwars.starwars.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.starwars.starwars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest(classes = PlanetRepository.class)
@DataJpaTest
class PlanetRepositoryTest {
  @Autowired
  private PlanetRepository repository;
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void createPlanet_WithValidData_ReturnsPlanet() {
    Planet planet = repository.save(PLANET);
    Planet sut =
            testEntityManager.find(Planet.class, planet.getId());
    assertThat(sut).isNotNull();
    assertThat(sut).isEqualTo(planet);
//    assertThat(sut.getName()).isEqualTo(planet.getName());
  }
  @Test
  void createPlanet_WithInvalidData_ThrowsException() {
    Planet emptyPlanet = new Planet();
    Planet invalidPlanet = new Planet("", "", "");
    assertThatThrownBy(() -> repository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
    assertThatThrownBy(() -> repository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
  }
  @Test
  void createPlanet_WithExistingName_ThrowsException() {
    //sem usar script data.sql
    Planet planet = testEntityManager.persistAndFlush(PLANET);
    testEntityManager.detach(planet);
    planet.setId(null);
    assertThatThrownBy(() -> repository.save(planet)).isInstanceOf(RuntimeException.class);

    //usando com script data.sql
    //assertThatThrownBy(()-> repository.save(PLANET)).isInstanceOf(RuntimeException.class);
  }
  @Test
  void getPlanet_ByExistingId_ReturnsPlanet(){
    Planet planet = testEntityManager.persistAndFlush(PLANET);
    Optional<Planet> optPlanet = repository.findById(planet.getId());
    assertThat(optPlanet).isNotEmpty();
    assertThat(planet).isEqualTo(optPlanet.get());
  }
  @Test
  void getPlanet_ByUnexistingId_ReturnsEmpty(){
    Optional<Planet> optPlanet = repository.findById(999L);
    assertThat(optPlanet).isEmpty();
  }
}