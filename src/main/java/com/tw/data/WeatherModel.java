package com.tw.data;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.function.BiConsumer;

@Data
public class WeatherModel {
    List<DayWeather> days = Lists.newArrayList();

    public void forEachByHours(BiConsumer<HourWeather, Integer> consumer) {
        int hourIdx = 0;
        for (DayWeather day : days) {
            for (HourWeather hourWeather : day.getHoursWeather()) {
                consumer.accept(hourWeather, hourIdx++);
            }
        }
    }
}
