package net.cglcapital.coininfo.service;

import net.cglcapital.coininfo.common.model.coinmarketcap.CoinCMCStatistic;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinName;

import java.util.List;

public interface CoinCrawler {

    CoinCMCStatistic getCoinPriceStatistic(String coinName, String cmcUrlSymbol);

    List<CoinName> getTopMarketCap();
}
