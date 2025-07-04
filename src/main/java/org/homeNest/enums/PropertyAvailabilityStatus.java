package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PropertyAvailabilityStatus {
    AVAILABLE("available"),
    ON_VIEW("on view");

    private final String title;
}
