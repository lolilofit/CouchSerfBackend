package main.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertType {
    HOUSE_SEARCH,
    HOUSE_PROVISION;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
