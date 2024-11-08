package org.example.coursework.enums;

public enum Role {
    CLIENT("client"),
    ROOT("root");

    private final String title;

    Role(String title) {
        this.title = title;
    }

    public String get() {
        return title;
    }
}