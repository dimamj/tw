package com.tw.service.parsers;

import com.tw.data.WeatherModel;
import org.jsoup.nodes.Element;

public interface DataParser {
    void parseAndLoad(Element weatherModalEl, WeatherModel model);
}
