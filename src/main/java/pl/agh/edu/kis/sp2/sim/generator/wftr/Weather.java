package pl.agh.edu.kis.sp2.sim.generator.wftr;

import pl.agh.edu.kis.sp2.sim.generator.enumeration.FogAmount;
import pl.agh.edu.kis.sp2.sim.generator.enumeration.WindDirection;

import java.math.BigDecimal;

public class Weather {
    private BigDecimal wind;
    private WindDirection windDirection;
    private FogAmount fog;
    private BigDecimal rainAmount;
    private BigDecimal temperature;

    public Weather() {
    }

    private Weather(Builder builder) {
        wind = builder.wind;
        windDirection = builder.windDirection;
        fog = builder.fog;
        rainAmount = builder.rainAmount;
        temperature = builder.temperature;
    }

    public BigDecimal getWind() {
        return wind;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public FogAmount getFog() {
        return fog;
    }

    public BigDecimal getRainAmount() {
        return rainAmount;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public static final class Builder {
        private BigDecimal wind;
        private WindDirection windDirection;
        private FogAmount fog;
        private BigDecimal rainAmount;
        private BigDecimal temperature;

        public Builder() {
        }

        public Builder wind(BigDecimal val) {
            wind = val;
            return this;
        }

        public Builder windDirection(WindDirection val) {
            windDirection = val;
            return this;
        }

        public Builder fog(FogAmount val) {
            fog = val;
            return this;
        }

        public Builder rainAmount(BigDecimal val) {
            rainAmount = val;
            return this;
        }

        public Builder temperature(BigDecimal val) {
            temperature = val;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }

    @Override
    public String toString() {
        return "WeatherDto{" +
                "wind=" + wind +
                ", windDirection=" + windDirection +
                ", fog=" + fog +
                ", rainAmount=" + rainAmount +
                ", temperature=" + temperature +
                '}';
    }
}
