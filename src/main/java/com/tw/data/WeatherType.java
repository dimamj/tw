package com.tw.data;

import com.google.common.collect.Sets;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Set;

public enum WeatherType {
    SUNNY(1f, 1), PARTLY_CLOUDY(2f, 2, 3), CLOUDY(2.5f, 4), HEAVY_RAIN(3.5f, 5, 6, 7), RAIN(3.0f, 18, 19, 20),
    SNOW(4.5f, 8, 9, 10), WET_SNOW(4.0f, 11, 12, 13), THUNDERSTORM(3.2f, 14, 21), FOG(0, 17), UNKNOWN(0, -1);

    private Set<Integer> ids;
    private float priority;

    WeatherType(float priority, Integer... ids) {
        Assert.notNull(ids, "Ids required");
        this.priority = priority;
        this.ids = Sets.newHashSet(ids);
    }

    public static WeatherType getById(int id) {
        return Arrays.stream(WeatherType.values())
                .filter(t -> t.ids.contains(id))
                .findFirst().orElse(UNKNOWN);
    }

    public float getPriority() {
        return priority;
    }
}
