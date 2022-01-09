package com.dearmanwj.skimapbackend.interpolator;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoundingBox {

    private static final double BUFFER = 0.0;

    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    public void setMinLatIfLower(final double lat) {
        minLat = Math.min(minLat, lat);
    }

    public void setMinLonIfLower(final double lon) {
        minLon = Math.min(minLon, lon);
    }

    public void setMaxLatIfHigher(final double lat) {
        maxLat = Math.max(maxLat, lat);
    }

    public void setMaxLonIfHigher(final double lon) {
        maxLon = Math.max(maxLon, lon);
    }

    public void mergeWith(final BoundingBox other) {
        setMinLatIfLower(other.minLat);
        setMinLonIfLower(other.minLon);
        setMaxLatIfHigher(other.maxLat);
        setMaxLonIfHigher(other.maxLon);
    }

    public boolean isPointInside(final double lat, final double lon) {
        return lat >= minLat - BUFFER
        && lat <= maxLat + BUFFER
        && lon >= minLon - BUFFER
        && lon <= maxLon + BUFFER;
    }
    
}
