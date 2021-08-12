package net.cglcapital.coininfo.consumer.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EventListenerHealth {

    private final Map<String, Boolean> listenerHealthMap = new HashMap<>();

    public void socketInterrupted(String listener) {
        listenerHealthMap.put(listener, false);
    }

    public Map<String, Boolean> getListenerHealthMap() {
        return listenerHealthMap;
    }
}
