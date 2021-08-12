package net.cglcapital.coininfo.consumer;

import com.binance.api.client.domain.event.AggTradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class AggTradeEventConsumerManagement implements EventConsumer<AggTradeEvent> {

    @Autowired
    private BreakoutAlertEventConsumer breakoutAlertEventConsumer;
    @Autowired
    private BreakdownAlertEventConsumer breakdownAlertEventConsumer;

    private final Executor executor = Executors.newFixedThreadPool(5);

    @Override
    public void consume(AggTradeEvent event) {
        executor.execute(() -> breakoutAlertEventConsumer.consume(event));
        executor.execute(() -> breakdownAlertEventConsumer.consume(event));
    }
}
