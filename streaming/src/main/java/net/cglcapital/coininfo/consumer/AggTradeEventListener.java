package net.cglcapital.coininfo.consumer;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.domain.event.AggTradeEvent;
import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.consumer.health.EventListenerHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AggTradeEventListener implements BinanceApiCallback<AggTradeEvent> {

    @Autowired
    private AggTradeEventConsumerManagement eventEventConsumerManagement;
    @Autowired
    private EventListenerHealth eventListenerHealth;

    @Override
    public void onResponse(AggTradeEvent event) {
        eventEventConsumerManagement.consume(event);
    }

    @Override
    public void onFailure(Throwable cause) {
        eventListenerHealth.socketInterrupted("AggTradeEventListener");
        log.error("[AggTradeEventListener] ERROR: " + cause.getMessage(), cause);
    }
}
