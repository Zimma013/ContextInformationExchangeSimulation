package pl.agh.edu.kis.sp2.sim.generator.dto;

import java.math.BigDecimal;

public class LocalizationDto {

    private BigDecimal latitude;
    private BigDecimal longitude;

    public LocalizationDto() {
    }

    private LocalizationDto(Builder builder) {
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

        public LocalizationDto build() {
            return new LocalizationDto(this);
        }
    }
}
