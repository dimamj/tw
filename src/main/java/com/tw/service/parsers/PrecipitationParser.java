package com.tw.service.parsers;

import com.tw.data.WeatherModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(5)
public class PrecipitationParser implements DataParser {
    @Override
    public void parseAndLoad(Element el, WeatherModel model) {
        Elements elements = el.select(".td-rain td");

        model.forEachByHours((hourWeather, idx) -> {
            String text = elements.get(idx).text();
            if (!StringUtils.isEmpty(text)) {
                hourWeather.setPrecipitationInCm(Float.valueOf(text));
            }
        });
    }
}
