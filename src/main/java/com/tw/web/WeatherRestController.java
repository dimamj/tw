package com.tw.web;

import com.tw.data.WeatherModel;
import com.tw.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class WeatherRestController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherModel getWeather(HttpServletRequest request) {
        return null;
    }

    @GetMapping("/weather-test")
    public WeatherModel getW() {
        return weatherService.getWeather(46.480F, 30.730F);
    }


}
