package com.starwars.starwars.service;

import com.starwars.starwars.domain.Planet;
import com.starwars.starwars.repository.PlanetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.starwars.starwars.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//Injeção de dependencia das beans
@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
class PlanetServiceTest {
  //@Autowired //SpringBootTest
  @InjectMocks
  private PlanetService service;
  //  @MockBean //SpringBootTest
  @Mock
  private PlanetRepository repository;

  //operação_estado_retorno
  @Test
  void createPlanet_withValidData_ReturnPlanet() {
    when(repository.save(PLANET)).thenReturn(PLANET);
    //System under test
    Planet sut = service.create(PLANET);
    assertThat(sut).isEqualTo(PLANET);
  }
  /*
   * dublês de teste
   * dummy - apenas para compilar, não é invocado
   *
   * fake - implementação funcional, mas não usada em produção
   * (ex: banco de em memória)
   *
   * stub - responde de acordo com definições preestabelecidas:
   * quando chamado com determinado(s) parâmetros(s) ->
   * então retorna valor fixo (verificação de estado)
   *
   * spy - gravam informações sobre como foram chamados,
   * "stub melhorado"(verificação de estado e comportamento)
   *
   * mock - interação exata com os objetos que o usam,
   * verificar se o fluxo desejado foi invocado (verificação
   * de comportamento)
   * */

  @Test
  void createPlanet_withInvalidData_ThrowsException() {
    // AAA - ARRANGE, ACE E ASSERT
    when(repository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
    assertThatThrownBy(
            () -> service.create(INVALID_PLANET)
    ).isInstanceOf(RuntimeException.class);
  }

  @Test
  void getPlanet_ByExistingId_ReturnsPlanet() {
//    PLANET.setId(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(PLANET));
    Optional<Planet> sut = service.findById(1L);
    assertThat(sut).isNotEmpty();
    assertThat(sut.get()).isEqualTo(PLANET);
  }

  @Test
  void getPlanet_ByUnexistingId_ReturnsEmpty() {
    when(repository.findById(1L)).thenReturn(Optional.empty());
    Optional<Planet> sut = service.findById(1L);
    assertThat(sut).isEmpty();
  }

  @Test
  void getPlanet_ByExistingName_ReturnsPlanet() {
    when(repository.findByName("Tatooine")).thenReturn(Optional.of(PLANET));
    Optional<Planet> sut = service.findByName("Tatooine");
    assertThat(sut).isNotEmpty();
    assertThat(sut.get()).isEqualTo(PLANET);
  }

  @Test
  void getPlanet_ByUnexistingName_ReturnsEmpty() {
    when(repository.findByName("")).thenReturn(Optional.empty());
    Optional<Planet> sut = service.findByName("");
    assertThat(sut).isEmpty();
  }

  @Test
  void listPlanets_ReturnsPlanets() {
    when(repository.findByClimateAndTerrain("", ""))
            .thenReturn(PLANET_LIST);
    List<Planet> sut = service.planetList("", "");
    assertThat(sut).isNotEmpty();
    assertThat(sut).hasSize(2);
    assertThat(sut).isEqualTo(PLANET_LIST);
  }

  @Test
  void listPlanets_ByTerrain_ReturnsOnePlanet() {
    String terrain = "Arid and hot";
    var listFiltered =
            PLANET_LIST.stream()
                    .filter(p -> p.getTerrain()
                            .equals(terrain)).toList();
    when(repository.findByClimateAndTerrain("", terrain))
            .thenReturn(listFiltered);
    List<Planet> sut = service.planetList("", terrain);
    assertThat(sut).isNotEmpty();
    assertThat(sut).hasSize(1);
    assertThat(sut).isEqualTo(listFiltered);
  }

  @Test
  void listPlanets_ByClimate_ReturnsOnePlanet() {
    String climate = "Forests";
    var listFiltered =
            PLANET_LIST.stream()
                    .filter(p -> p.getClimate()
                            .equals(climate)).toList();
    when(repository.findByClimateAndTerrain(climate, ""))
            .thenReturn(listFiltered);
    List<Planet> sut = service.planetList("Forests", "");
    assertThat(sut).isNotEmpty();
    assertThat(sut).hasSize(1);
    assertThat(sut).isEqualTo(listFiltered);
  }

  @Test
  void listPlanets_ByClimateAndTerrain_ReturnsOnePlanet() {
    String climate = "Forests";
    String terrain = "Temperate";
    var listFiltered =
            PLANET_LIST.stream()
                    .filter(p ->
                            p.getClimate().equals(climate) &&
                                    p.getTerrain().equals(terrain)
                    ).toList();
    when(repository.findByClimateAndTerrain(climate, terrain))
            .thenReturn(listFiltered);
    List<Planet> sut = service.planetList(climate, terrain);
    assertThat(sut).isNotEmpty();
    assertThat(sut).hasSize(1);
    assertThat(sut).isEqualTo(listFiltered);
  }

  @Test
  void listPlanets_ByNotExistingClimateAndTerrain_ReturnsEmpty() {
    String climate = "notExists";
    String terrain = "notExists";
    var listFiltered =
            PLANET_LIST.stream()
                    .filter(p ->
                            p.getClimate().equals(climate) &&
                                    p.getTerrain().equals(terrain)
                    ).toList();
    //pode-se usar any()
    //no lugar
    when(repository.findByClimateAndTerrain(climate, terrain))
            .thenReturn(listFiltered);
    List<Planet> sut = service.planetList(climate, terrain);
    assertThat(sut).isEmpty();
    assertThat(sut).hasSize(0);
    assertThat(sut).isEqualTo(listFiltered);
  }
  @Test
  void removePlanet_withInvalidId_ThrowsException() {
    assertThatThrownBy(
            () -> service.remove(1L)
    ).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void removePlanet_ByExistingId_ReturnsPlanet() {
    when(repository.findById(1L)).thenReturn(Optional.of(PLANET));
    assertThatCode(()-> service.remove(1L)).doesNotThrowAnyException();
  }
}