package org.example.coursework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HeatingType {

    CENTRAL("central"),
    ELECTRICAL("electrical"),
    HYDRONIC("hydronic"),
    STEAM("steam");

    private final String title;
}

