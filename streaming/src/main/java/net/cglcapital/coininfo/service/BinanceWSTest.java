package net.cglcapital.coininfo.service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.DepthEvent;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.TickerStatistics;

import java.io.IOException;
import java.util.List;

public class BinanceWSTest {

    private static final String API_KEY = "GA4DupAQPeHl2bgO7zqTDwL9tTGpmbOob4imTh0a5DpM7EiHTbiTGuWMciSsvf2P";
    private static final String API_SECRET = "yBZPNwOIkBgG9keL6VcFFfNcYxYYCHiVZrMPLywC8RkxifRMa3DBcKt8equTIupl";

    public static void listenEvent() throws IOException {
        BinanceApiWebSocketClient wsClient = BinanceApiClientFactory.newInstance(API_KEY, API_SECRET).newWebSocketClient();
        wsClient.onAggTradeEvent("btcusdt", System.out::println);

        wsClient.onDepthEvent("btcusdt", (DepthEvent response) -> {
            System.out.println(response.getAsks());
        });

        wsClient.onCandlestickEvent("btcusdt", CandlestickInterval.EIGHT_HOURLY, System.out::println);

    }

    public static void main(String[] args) throws IOException {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(API_KEY, API_SECRET);
        BinanceApiRestClient client = factory.newRestClient();
        long serverTime = client.getServerTime();
        System.out.println(serverTime);
        TickerStatistics tickerStatistics = client.get24HrPriceStatistics("BTCUSDT");
        System.out.println(tickerStatistics);

        List<Candlestick> candlesticks = client.getCandlestickBars("BTCUSDT", CandlestickInterval.DAILY);
        System.out.println(candlesticks);

        String listenKey = client.startUserDataStream();
        client.keepAliveUserDataStream(listenKey);
        client.closeUserDataStream(listenKey);
        //listenEvent();
    }
}
