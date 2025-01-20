package org.example.coursework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingConstructionType {

    MONOLITHIC("Monolithic"),
    BRICK("Brick"),
    PANEL("Panel"),
    FRAME("Frame"), //каркас
    BLOCK("Block"),
    HYBRID("Hybrid");

    private final String title;
}
