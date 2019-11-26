package pl.agh.edu.kis.sp2.sim.generator.wftr;

import pl.agh.edu.kis.sp2.sim.generator.enumeration.*;

import java.math.BigDecimal;
import java.util.Random;

public class Weather {

    private Wind wind;
    private WindDirection windDirection;
    private Fog fog;
    private Rain rainAmount;
    private Temperature temperature;

    public Weather() {
    }

    private Weather(Builder builder) {
        wind = builder.wind;
        windDirection = builder.windDirection;
        fog = builder.fog;
        rainAmount = builder.rainAmount;
        temperature = builder.temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public Fog getFog() {
        return fog;
    }

    public Rain getRainAmount() {
        return rainAmount;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public static final class Builder {
        private Wind wind;
        private WindDirection windDirection;
        private Fog fog;
        private Rain rainAmount;
        private Temperature temperature;

        public Builder() {
        }

        public Builder wind(Wind val) {
            wind = val;
            return this;
        }

        public Builder windDirection(WindDirection val) {
            windDirection = val;
            return this;
        }

        public Builder fog(Fog val) {
            fog = val;
            return this;
        }

        public Builder rainAmount(Rain val) {
            rainAmount = val;
            return this;
        }

        public Builder temperature(Temperature val) {
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
