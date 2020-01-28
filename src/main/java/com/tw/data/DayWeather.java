package com.tw.data;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class DayWeather {
    private LocalDate date;
    private List<HourWeather> hoursWeather = Lists.newArrayList();

    public DayWeather(LocalDate date) {
        this.date = date;
    }
}
