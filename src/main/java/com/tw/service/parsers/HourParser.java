package com.tw.service.parsers;

import com.tw.data.HourWeather;
import com.tw.data.WeatherModel;
import org.jsoup.nodes.Element;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class HourParser implements DataParser {
    @Override
    public void parseAndLoad(Element el, WeatherModel model) {
        int idx = 0;

        for (Element tdHour : el.select(".td-hour td")) {
            short hourVal = Short.valueOf(tdHour.text());
            model.getDays().get(idx).getHoursWeather().add(new HourWeather(hourVal));

            if (tdHour.hasClass("day-end")) idx++;
        }
    }
}
