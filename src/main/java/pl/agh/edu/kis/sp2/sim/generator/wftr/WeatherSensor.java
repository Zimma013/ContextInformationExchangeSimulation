package pl.agh.edu.kis.sp2.sim.generator.wftr;

import pl.agh.edu.kis.sp2.sim.generator.enumeration.*;

public class WeatherSensor {

	private Weather weather;
	private Localization localization;
	private int weatherConditionSimulationValue;
	private Avalanche avalanche;

	private WeatherSensor(Builder builder) {
		weather = builder.weather;
		localization = builder.localization;
		weatherConditionSimulationValue = builder.weatherConditionSimulationValue;
		avalanche = builder.avalanche;
	}

	public Weather getWeather() {
		return weather;
	}

	public Localization getLocalization() {
		return localization;
	}

	public int getWeatherConditionSimulationValue() {
		return weatherConditionSimulationValue;
	}

	public Avalanche getAvalanche() {
		return avalanche;
	}

	public static final class Builder {
		private Weather weather;
		private Localization localization;
		private int weatherConditionSimulationValue;
		private Avalanche avalanche;

		public Builder() {
		}

		public Builder weather(Weather val) {
			weather = val;
			return this;
		}

		public Builder localization(Localization val) {
			localization = val;
			return this;
		}

		public Builder weatherConditionSimulationValue(int val) {
			weatherConditionSimulationValue = val;
			return this;
		}

		public Builder avalanche(Avalanche val) {
			avalanche = val;
			return this;
		}

		public WeatherSensor build() {
			return new WeatherSensor(this);
		}
	}

	public void generateWeatherData(int weatherConditionSimulationValue) {
		switch (weatherConditionSimulationValue) {
			case 1:
			case 2:
			case 3:
				this.weather = generateWeather();
				break;
			case 4:
			case 5:
				this.weather = generateWeather();
				this.avalanche = Avalanche.getRandomAvalanche();
				break;
			default:
				break;
		}
	}

	private Weather generateWeather() {
		return new Weather.Builder()
				.fog(Fog.getRandomFog())
				.rainAmount(Rain.getRandomRain())
				.temperature(Temperature.getRandomTemperature())
				.wind(Wind.getRandomWind())
				.windDirection(WindDirection.randomWindDirection())
				.build();
	}
}
