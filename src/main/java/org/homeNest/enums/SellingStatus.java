package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SellingStatus {
    BUY("Buy"),
    RENT("Rent");

    private final String text;
}
