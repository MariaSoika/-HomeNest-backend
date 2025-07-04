package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StateOfRepair {
    NEW("New"),
    COSMETIC("Cosmetic"),
    NO_REPAIR("No Repair");

    private final String title;
}
