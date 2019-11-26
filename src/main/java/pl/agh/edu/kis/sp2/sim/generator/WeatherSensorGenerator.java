package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.wftr.WeatherSensor;

import java.util.ArrayList;
import java.util.List;

public class WeatherSensorGenerator {

	private LocalizationGenerator localizationGenerator = new LocalizationGenerator();

	public List<WeatherSensor> generateWeatherSensors(int count, int weatherConditionSimulationValue) {
		List<WeatherSensor> result = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			WeatherSensor sensor = new WeatherSensor.Builder()
					.localization(localizationGenerator.generateLocalization())
					.weatherConditionSimulationValue(weatherConditionSimulationValue)
					.build();

			sensor.generateWeatherData(sensor.getWeatherConditionSimulationValue());

			result.add(sensor);
		}

		return result;
	}
}
