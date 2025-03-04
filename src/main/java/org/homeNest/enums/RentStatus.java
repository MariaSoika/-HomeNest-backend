package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RentStatus {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String text;
}