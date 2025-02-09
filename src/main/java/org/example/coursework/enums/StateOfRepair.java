package org.example.coursework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StateOfRepair {
    NEW("New"),
    RENOVATED("Renovated"),
    COSMETIC_REPAIR("Cosmetic Repair"),
    REQUIRES_REPAIR("Requires"),
    NO_REPAIR("No Repair");

    private final String title;
}
