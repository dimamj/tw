package com.tw.web;

import com.tw.data.GeoIpData;
import com.tw.data.WeatherModel;
import com.tw.service.GeoIpService;
import com.tw.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherRestController {
    private final WeatherService weatherService;
    private final GeoIpService geoIpService;

    @GetMapping
    public WeatherResult getWeather(HttpServletRequest request) {
        GeoIpData geoIpData = geoIpService.getGeoData(request);
        WeatherModel weather = weatherService.getWeather(geoIpData.getLatitude(), geoIpData.getLongitude());
        return new WeatherResult(weather, geoIpData);
    }

    @Data
    @AllArgsConstructor
    public static class WeatherResult {
        private WeatherModel weatherResult;
        private GeoIpData geoIpData;
    }
}
