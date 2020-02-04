package com.tw.service;

import com.google.common.collect.Lists;
import com.tw.data.HourWeather;
import com.tw.data.WeatherModel;
import com.tw.data.WeatherType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private static final double WIND_FROM_KT_TO_MS_FACTOR = 0.5144444;
    private final WindyWeatherParser windyWeatherParser;

    public WeatherModel getWeather(float latitude, float longitude) {
        List<WeatherModel> weatherModels = windyWeatherParser.getWeatherModels(latitude, longitude);
        WeatherModel result = null;

        if (!CollectionUtils.isEmpty(weatherModels)) {
            result = weatherModels.remove(0);
            mergeWeatherModels(result, weatherModels);
        }

        return result;
    }

    private void mergeWeatherModels(WeatherModel result, List<WeatherModel> models) {
        for (int d = 0; d < result.getDays().size(); d++) {
            var dayWeather = result.getDays().get(d);
            for (int h = 0; h < dayWeather.getHoursWeather().size(); h++) {
                var resHourWeather = dayWeather.getHoursWeather().get(h);
                var hourWeathers = Lists.newArrayList(resHourWeather);

                for (WeatherModel model : models) {
                    hourWeathers.add(model.getDays().get(d).getHoursWeather().get(h));
                }

                var temp = hourWeathers.stream().mapToInt(HourWeather::getTemperature).average();
                var windSpeed = hourWeathers.stream().mapToInt(HourWeather::getWindSpeed).average();
                var precipitation = hourWeathers.stream().mapToDouble(HourWeather::getPrecipitationInCm).average();

                resHourWeather.setTemperature((short) Math.round(temp.orElse(0)));
                resHourWeather.setWindSpeed((short) Math.round(windSpeed.orElse(0) * WIND_FROM_KT_TO_MS_FACTOR));
                resHourWeather.setPrecipitationInCm((float) Math.round(precipitation.orElse(0) * 100) / 100);

                resHourWeather.setWeatherType(getFrequentWeatherType(hourWeathers));
            }
        }
    }

    private WeatherType getFrequentWeatherType(List<HourWeather> hourWeathers) {
        Map<WeatherType, Long> weatherTypeGroup = hourWeathers.stream()
                .collect(Collectors.groupingBy(HourWeather::getWeatherType, Collectors.counting()));

        long maxCount = weatherTypeGroup.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
        List<WeatherType> frequentWeatherTypes = weatherTypeGroup.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparingDouble(WeatherType::getPriority).reversed())
                .collect(Collectors.toList());

        return frequentWeatherTypes.get(0);
    }
}
