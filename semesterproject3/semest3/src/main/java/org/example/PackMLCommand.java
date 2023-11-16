package org.example;

public enum PackMLCommand {
    START ("DI3"),
    STOP ("DI4"),
    ABORT("DI5"),
    CLEAR("DI6");

    private final String buttonName;

    PackMLCommand(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

}
