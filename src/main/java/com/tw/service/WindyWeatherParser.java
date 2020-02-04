package com.tw.service;

import com.google.common.collect.Lists;
import com.tw.data.DayWeather;
import com.tw.data.WeatherModel;
import com.tw.service.parsers.DataParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class WindyWeatherParser {
    private final WebDriver driver;
    private final WebDriverWait driverWait;
    private final List<DataParser> dataParsers;

    public WindyWeatherParser(@Value("${web-driver.path}") String webDriverPath,
                              List<DataParser> dataParsers) {
        System.setProperty("webdriver.gecko.driver", webDriverPath);
        var firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(true);

        this.driver = new FirefoxDriver(firefoxOptions);
        this.driverWait = new WebDriverWait(driver, 5);
        this.dataParsers = dataParsers;
    }

    @PreDestroy
    private void preDestroy() {
        driver.quit();
    }

    public List<WeatherModel> getWeatherModels(float latitude, float longitude) {
        var url = String.format(Locale.US, "https://www.windy.com/multimodel/%f/%f?%1$f,%2$f,10", latitude, longitude);

        driver.get(url);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".model-box > .forecast-table")));

        Document doc = Jsoup.parse(driver.getPageSource());
        Elements elements = doc.select(".model-box > .forecast-table");

        var result = new ArrayList<WeatherModel>();

        elements.forEach(el -> {
            var model = new WeatherModel();

            el.select(".td-days td").forEach(tdDay -> {
                var date = LocalDate.parse(tdDay.attr("data-day"));
                model.getDays().add(new DayWeather(date));
            });

            dataParsers.forEach(parser -> parser.parseAndLoad(el, model));

            result.add(model);
        });

        return result;
    }
}
