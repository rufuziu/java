package com.starwars.starwars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.starwars.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.starwars.starwars.common.PlanetConstants.INVALID_PLANET;
import static com.starwars.starwars.common.PlanetConstants.PLANET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanetController.class)
class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private PlanetService service;

  @Test
  void createPlanet_WithValidData_ReturnsCreated() throws Exception {
    when(service.create(PLANET)).thenReturn(PLANET);

    mockMvc.perform(post("/planets")
                    .content(objectMapper.writeValueAsString(PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
    mockMvc.perform(post("/planets")
                    .content(objectMapper.writeValueAsString(INVALID_PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void createPlanet_WithExistingName_ReturnsBadRequest() throws Exception {
    when(service.create(any())).thenThrow(DataIntegrityViolationException.class);

    mockMvc.perform(post("/planets")
                    .content(objectMapper.writeValueAsString(PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
  }

  @Test
  void getPlanet_ByExistingId_ReturnsBadRequest() throws Exception {
    when(service.findById(1L)).thenReturn(Optional.of(PLANET));
    mockMvc.perform(get("/planets/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  void getPlanet_ByUnexistingId_ReturnsNotFound() throws Exception {
    when(service.findById(any())).thenReturn(Optional.empty());
    mockMvc.perform(get("/planets/{id}", 1))
            .andExpect(status().isNotFound());
  }

  @Test
  void getPlanet_ByExistingName_ReturnsOk(){}
  @Test
  void getPlanet_ByUnexistingName_ReturnsNotFound(){}
  @Test
  void getListOfPlanets_ReturnsOk(){}
  @Test
  void getListOfPlanets_ByClimate_ReturnsOk(){}
  @Test
  void getListOfPlanets_ByTerrain_ReturnsOk(){}
  @Test
  void getListOfPlanets_ByClimateAndTerrain_ReturnsOk(){}
  @Test
  void getListOfPlanets_ByUnexistingClimate_ReturnsOk(){}
  @Test
  void getListOfPlanets_ByUnexistingTerrain_ReturnsOk(){}
  @Test
  void getListOfPlanets_ByUnexistingClimateAndTerrain_ReturnsOk(){}
  @Test
  void deletePlanet_ByExistingId_ReturnsNoContent(){}
  @Test
  void deletePlanet_ByUnexistingId_ReturnsNoContent(){}
}