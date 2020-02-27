package com.mc.citicraft.web.service;

import com.mc.citicraft.web.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ConnectorService implements IConnectorService {

    public static final String message = " cannot be empty";
    private Map<String, Set<String>> map = new ConcurrentHashMap<>();

    @Override
    public void init(List<AbstractMap.SimpleEntry<String, String>> data) {
        data.forEach(x -> addLeg(x));
    }

    @Override
    public boolean isInitialized() {
        return !map.isEmpty();
    }

    @Override
    public ConnectorServiceResult work(String from, String to) {
        String s1 = from.trim();
        String s2 = to.trim();

        if("".equals(s1)) {
            throw new BadRequestException("from" + message);

        }

        if("".equals(s2)) {
            throw new BadRequestException("to" + message);

        }

        boolean haveFrom = map.containsKey(s1);
        boolean haveTo = map.containsKey(s2);

        return new ConnectorServiceResult(s1, s2, haveFrom, haveTo, (haveFrom && haveTo) ? map.get(s1).contains(s2) : false);
    }

    @Override
    public long citiesCount() {
        return map.size();
    }

    private void addLeg(AbstractMap.SimpleEntry<String, String> leg) {
        addConnection(leg.getKey(), leg.getValue(), com.google.common.collect.Sets.newHashSet(leg.getKey(), leg.getValue()));
    }

    private void addConnection(String a, String b, Set<String> set) {
        if(map.containsKey(a)) {
            set.addAll(map.get(a));
        }

        if(map.containsKey(b)) {
            set.addAll(map.get(b));
        }

        set.forEach(x -> map.put(x, set));
    }
}
