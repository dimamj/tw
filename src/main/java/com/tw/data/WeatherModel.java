package com.tw.data;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.function.BiConsumer;

@Data
public class WeatherModel {
    List<DayWeather> days = Lists.newArrayList();

    public void forEachByHours(BiConsumer<HourWeather, Integer> consumer) {
        for (int d = 0; d < days.size(); d++) {
            DayWeather dayWeather = days.get(d);

            for (int h = 0; h < dayWeather.getHoursWeather().size(); h++) {
                int idx = d + h;
                HourWeather hourWeather = dayWeather.getHoursWeather().get(h);
                consumer.accept(hourWeather, idx);
            }
        }
    }
}
