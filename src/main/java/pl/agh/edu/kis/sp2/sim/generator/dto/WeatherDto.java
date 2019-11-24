package pl.agh.edu.kis.sp2.sim.generator.dto;

import pl.agh.edu.kis.sp2.sim.generator.enumeration.FogAmount;
import pl.agh.edu.kis.sp2.sim.generator.enumeration.WindDirection;

import java.math.BigDecimal;

public class WeatherDto {
    private BigDecimal wind;
    private WindDirection windDirection;
    private FogAmount fog;
    private BigDecimal rainAmount;
    private BigDecimal temperature;
}
