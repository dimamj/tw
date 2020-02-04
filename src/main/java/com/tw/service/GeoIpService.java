package com.tw.service;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import com.tw.data.GeoIpData;
import com.tw.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class GeoIpService {
    private final DatabaseReader dbReader;
    private String localIp;

    public GeoIpService(@Value("${maxmind.db-path}") String dbPath, Environment env) throws MalformedURLException {
        if (env.acceptsProfiles(Profiles.of("local"))) {
            var whatIsMyIp = new URL("http://checkip.amazonaws.com");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(whatIsMyIp.openStream()))) {
                localIp = in.readLine();
            } catch (Exception e) {
                log.error("Can't get local ip", e);
            }
        }

        var pathToDb = Paths.get(dbPath);
        if (!Files.exists(pathToDb)) {
            throw new RuntimeException("Maxmind db file not found. Please add maxmind db file to " + dbPath);
        }

        try {
            this.dbReader = new DatabaseReader.Builder(pathToDb.toFile()).withCache(new CHMCache()).build();
        } catch (IOException e) {
            throw new RuntimeException("Maxmind build fail ", e);
        }
    }

    public GeoIpData getGeoData(HttpServletRequest request) {
        var ip = StringUtils.hasText(localIp) ? localIp : RequestUtils.getIp(request);
        try {
            CityResponse response = dbReader.city(InetAddress.getByName(ip));
            var location = response.getLocation();

            return new GeoIpData(location.getLatitude().floatValue(), location.getLongitude().floatValue(),
                    response.getCity().getName());
        } catch (IOException | GeoIp2Exception e) {
            log.error("Can't get geo data", e);
            return null;
        }
    }
}
