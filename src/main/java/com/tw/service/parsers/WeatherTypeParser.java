package com.tw.service.parsers;

import com.tw.data.WeatherModel;
import com.tw.data.WeatherType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2)
public class WeatherTypeParser implements DataParser {
    private static final Pattern imgIdxPattern = Pattern.compile("(\\d+)(_night_\\d+)?\\.png$");

    @Override
    public void parseAndLoad(Element el, WeatherModel model) {
        Elements elements = el.select(".td-icon td");

        model.forEachByHours((hourWeather, idx) -> {
            Element img = elements.get(idx).child(0);
            Matcher matcher = imgIdxPattern.matcher(img.attr("src"));
            WeatherType weatherType = WeatherType.UNKNOWN;

            if (matcher.find()) {
                int imgId = Integer.valueOf(matcher.group(1));
                weatherType = WeatherType.getById(imgId);
            }

            hourWeather.setWeatherType(weatherType);
        });
    }
}
