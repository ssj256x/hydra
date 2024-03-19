package org.lang;

public enum ColorPrint {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    GRAY("\u001B[37m");

    private final String color;
    private static final String RESET = "\u001B[0m";

    ColorPrint(String color) {
        this.color = color;
    }

    public void println(Object text) {
        System.out.println(STR."\{color}\{text}\{RESET}");
    }

    public void print(Object text) {
        System.out.print(STR."\{color}\{text}\{RESET}");
    }
}
