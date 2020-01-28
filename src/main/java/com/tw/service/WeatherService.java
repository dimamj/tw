package com.tw.service;

import com.google.common.collect.Lists;
import com.tw.data.DayWeather;
import com.tw.data.HourWeather;
import com.tw.data.WeatherModel;
import com.tw.data.WeatherType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {
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
            DayWeather dayWeather = result.getDays().get(d);
            for (int h = 0; h < dayWeather.getHoursWeather().size(); h++) {
                HourWeather resHourWeather = dayWeather.getHoursWeather().get(h);
                List<HourWeather> hourWeathers = Lists.newArrayList(resHourWeather);

                for (WeatherModel model : models) {
                    hourWeathers.add(model.getDays().get(d).getHoursWeather().get(h));
                }

                OptionalDouble temp = hourWeathers.stream().mapToInt(HourWeather::getTemperature).average();
                OptionalDouble windSpeed = hourWeathers.stream().mapToInt(HourWeather::getWindSpeed).average();
                OptionalDouble precipitation = hourWeathers.stream().mapToDouble(HourWeather::getPrecipitationInCm).average();

                resHourWeather.setTemperature((short) Math.round(temp.orElse(0)));
                resHourWeather.setWindSpeed((short) Math.round(windSpeed.orElse(0)));
                resHourWeather.setPrecipitationInCm((float) precipitation.orElse(0));

                resHourWeather.setWeatherType(getFrequentWeatherType(hourWeathers));
            }
        }
    }

    private WeatherType getFrequentWeatherType(List<HourWeather> hourWeathers){
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
