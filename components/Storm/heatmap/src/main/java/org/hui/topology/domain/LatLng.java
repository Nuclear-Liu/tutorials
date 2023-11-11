package org.hui.topology.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class LatLng implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int DEFAULT_PRECISION = 6;

    private BigDecimal lat, lng;

    public LatLng() {
    }

    public LatLng(final BigDecimal lat, final BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng(final String lat, final String lng) {
        this.lat = new BigDecimal(lat);
        this.lng = new BigDecimal(lng);
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String toUrlValue() {
        return toUrlValue(DEFAULT_PRECISION);
    }
    public String toUrlValue(int precision) {
        return lat.setScale(precision, BigDecimal.ROUND_HALF_EVEN).toString() + "," + lng.setScale(precision, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LatLng latLng = (LatLng) o;

        if (!Objects.equals(lat, latLng.lat)) return false;
        if (!Objects.equals(lng, latLng.lng)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LatLng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
