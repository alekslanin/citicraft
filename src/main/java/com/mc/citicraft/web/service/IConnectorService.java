package com.mc.citicraft.web.service;

import java.util.AbstractMap;
import java.util.List;

public interface IConnectorService {
    void init(List<AbstractMap.SimpleEntry<String, String>> data);
    boolean isInitialized();
    ConnectorServiceResult work(String from, String to);
    long citiesCount();
}
