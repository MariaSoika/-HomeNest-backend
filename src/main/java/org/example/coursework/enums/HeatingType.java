package org.example.coursework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HeatingType {

    CENTRAL("central"),
    INDIVIDUAL("individual"),
    GAS("gas"),
    ELECTRIC("electric"),;

    private final String title;
}

