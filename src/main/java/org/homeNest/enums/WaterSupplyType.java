package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WaterSupplyType {
    CENTRALIZED("Centralized"),
    AUTONOMOUS("Autonomous"),
    WELL("Well"); //private water source

    private final String title;
}
