package org.example.coursework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    CLIENT("client"),
    ROOT("root");

    private final String title;
}