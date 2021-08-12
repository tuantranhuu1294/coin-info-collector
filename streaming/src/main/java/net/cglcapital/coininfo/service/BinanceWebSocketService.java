package net.cglcapital.coininfo.service;

public interface BinanceWebSocketService {

    void listenAggTradeEvent(String... symbols);
}
