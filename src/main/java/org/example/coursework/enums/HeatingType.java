package org.example.coursework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HeatingType {

    CENTRAL("Central"),
    INDIVIDUAL("Individual"),
    GAS("Gas"),
    ELECTRIC("Electric"),;

    private final String title;
}

