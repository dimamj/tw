package com.tw.service.parsers;

import com.tw.data.WeatherModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class WindSpeedParser implements DataParser {

    @Override
    public void parseAndLoad(Element el, WeatherModel model) {
        Elements elements = el.select(".td-wind td");

        model.forEachByHours((hourWeather, idx) ->
                hourWeather.setWindSpeed(Short.valueOf(elements.get(idx).text()))
        );
    }
}
