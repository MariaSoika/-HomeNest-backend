package org.homeNest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingConstructionType {

    MONOLITHIC("Monolithic"),
    BRICK("Brick"),
    PANEL("Panel"),
    FRAME("Frame"), //каркас
    BLOCK("Block");

    private final String title;
}
