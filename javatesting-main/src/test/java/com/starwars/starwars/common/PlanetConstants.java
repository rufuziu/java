package com.starwars.starwars.common;

import com.starwars.starwars.domain.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstants {
  public static final Planet PLANET =
          new Planet(
                  "Tatooine",
                  "Hot and arid",
                  "Historic");
  public static final Planet INVALID_PLANET =
          new Planet(
                  "",
                  "",
                  "");
  public static final List<Planet> PLANET_LIST =
          new ArrayList<>() {{
            add(new Planet(
                    "Tatooine",
                    "Desert",
                    "Arid and hot"
            ));
            add(new Planet(
                    "Corellia",
                    "Forests",
                    "Temperate"
            ));
          }};
}
