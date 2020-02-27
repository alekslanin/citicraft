package com.mc.citicraft.web.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class ConnectorServiceResult {

    private String from, to;
    private boolean isFromValid, isToValid, areConnected;

    public static final String message = " is not on the map";

    public ConnectorServiceResult(String from, String to, boolean isFromValid, boolean isToValid, boolean areConnected) {
        this.areConnected = areConnected;
        this.to = to;
        this.from = from;
        this.isFromValid = isFromValid;
        this.isToValid = isToValid;
    }

    @Override
    public String toString() {
        return areConnected ? "yes" : "no";
    }

    public String text() {
        if(!isFromValid) {
            return from + message;
        }

        if(!isToValid) {
            return to + message;
        }

        return String.format("%s and %s %s connected", from, to, areConnected ? "are" : "are not");
    }

    public boolean isOk() {
        return areConnected;
    }
}
