package net.cglcapital.coininfo.service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.consumer.AggTradeEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BinanceWebSocketServiceImpl implements BinanceWebSocketService {

    @Value("${service.binance.auth.api-key}")
    private String apiKey;
    @Value("${service.binance.auth.api-secret}")
    private String apiSecret;

    private List<Thread> runningThreads = new ArrayList<>();

    @Autowired
    private AggTradeEventListener aggTradeEventListener;

    @Override
    public void listenAggTradeEvent(String... symbols) {
        BinanceApiWebSocketClient wsClient = BinanceApiClientFactory.newInstance(apiKey, apiSecret).newWebSocketClient();
        Thread thread = new AggTradeEventHandlingThread(wsClient, aggTradeEventListener, symbols);
        thread.start();
        runningThreads.add(thread);
    }

    @PreDestroy
    public void destroy() {
        log.info("Pre-destroy close: {} running threads...", runningThreads.size());
        runningThreads.forEach(Thread::interrupt);
        runningThreads = new ArrayList<>();
    }

    public static class AggTradeEventHandlingThread extends Thread {

        private Closeable closeable;
        private final BinanceApiWebSocketClient webSocketClient;
        private final String[] symbols;
        private final AggTradeEventListener listener;

        public AggTradeEventHandlingThread(BinanceApiWebSocketClient webSocketClient, AggTradeEventListener listener,
                                           String[] symbols) {
            this.webSocketClient = webSocketClient;
            this.symbols = symbols;
            this.listener = listener;
        }

        @Override
        public void run() {
            String joinSymbol = String.join(",", symbols);
            closeable = webSocketClient.onAggTradeEvent(joinSymbol, listener);
            log.info("Listening agg trade events...");
        }

        @Override
        public void interrupt() {
            log.info("Closing running threads");
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            super.interrupt();
        }

    }
}
