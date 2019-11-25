package pl.agh.edu.kis.sp2.sim.generator.wftr;

import java.math.BigDecimal;

public class Localization {

    private BigDecimal latitude;
    private BigDecimal longitude;

    public Localization() {
    }

    private Localization(Builder builder) {
        latitude = builder.latitude;
        longitude = builder.longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public static final class Builder {
        private BigDecimal latitude;
        private BigDecimal longitude;

        public Builder() {
        }

        public Builder latitude(BigDecimal val) {
            latitude = val;
            return this;
        }

        public Builder longitude(BigDecimal val) {
            longitude = val;
            return this;
        }

        public Localization build() {
            return new Localization(this);
        }
    }

    @Override
    public String toString() {
        return "LocalizationDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
