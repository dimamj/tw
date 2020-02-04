package com.tw.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoIpData {
    private float latitude;
    private float longitude;
    private String cityName;
}
