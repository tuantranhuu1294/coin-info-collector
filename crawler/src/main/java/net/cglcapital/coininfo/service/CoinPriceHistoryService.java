package net.cglcapital.coininfo.service;

import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;

import java.util.List;

public interface CoinPriceHistoryService {

    List<PriceHistoryDTO> getPriceHistory(String symbol, int limit, long startTime, long endTime);

    CoinDTO getCoinPriceStatistic(String coinCode, String cmcUrlSymbol);

    void getListCoins();
}
