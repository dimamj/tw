package com.tw.data;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HourWeather {
    private short hour;
    private WeatherType weatherType;
    private short temperature;
    private short windSpeed;
    private float precipitationInCm;

    public HourWeather(short hour) {
        this.hour = hour;
    }
}
