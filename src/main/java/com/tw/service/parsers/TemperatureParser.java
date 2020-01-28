package com.tw.service.parsers;

import com.tw.data.WeatherModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class TemperatureParser implements DataParser {
    @Override
    public void parseAndLoad(Element el, WeatherModel model) {
        Elements elements = el.select(".td-temp td");

        model.forEachByHours((hourWeather, idx) -> {
            short temp = Short.valueOf(elements.get(idx).text().replaceAll("°", ""));
            hourWeather.setTemperature(temp);
        });
    }
}
