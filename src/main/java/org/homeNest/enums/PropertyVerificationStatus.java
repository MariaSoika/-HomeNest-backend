package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PropertyVerificationStatus {
    VERIFIED("Verified"),
    WAITING("Waiting");

    private final String title;
}
