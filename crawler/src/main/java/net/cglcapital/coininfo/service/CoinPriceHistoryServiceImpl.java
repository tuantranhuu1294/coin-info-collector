package net.cglcapital.coininfo.service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;
import net.cglcapital.coininfo.common.db.domain.dto.StatisticDTO;
import net.cglcapital.coininfo.common.db.mapper.CoinMapper;
import net.cglcapital.coininfo.common.db.mapper.StatisticMapper;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinCMCStatistic;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinName;
import net.cglcapital.coininfo.mapper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoinPriceHistoryServiceImpl implements CoinPriceHistoryService {

    @Autowired
    private CoinCrawler coinCrawler;
    @Autowired
    private CoinInfoDao coinInfoDao;

    @Value("${service.binance.auth.api-key}")
    private String binanceApiKey;
    @Value("${service.binance.auth.api-secret}")
    private String binanceApiSecret;

    private BinanceApiRestClient binanceApiRestClient;

    @PostConstruct
    private void init() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binanceApiKey, binanceApiSecret);
        this.binanceApiRestClient = factory.newRestClient();
        log.info("Binance api rest client is initialized");
    }

    @Override
    public List<PriceHistoryDTO> getPriceHistory(String symbol, int limit, long startTime, long endTime) {
        log.info("Getting candlestick bars daily price of symbol: {}, limit: {}, startTime: {}, endTime: {}",
            symbol, limit, startTime, endTime);
        List<Candlestick> candlesticks = binanceApiRestClient.getCandlestickBars(symbol, CandlestickInterval.DAILY,
            limit, startTime, endTime);

        return ResponseMapper.MAPPER.candlesticksToPriceHistoryDTOs(candlesticks);
    }

    @Override
    public CoinDTO getCoinPriceStatistic(String coinCode, String cmcUrlSymbol) {
        CoinCMCStatistic coinCMCStatistic = coinCrawler.getCoinPriceStatistic(coinCode, cmcUrlSymbol);
        StatisticDTO statisticDTO = StatisticMapper.MAPPER.priceHistoryToStatisticDTO(coinCMCStatistic);
        CoinDTO coinDTO = new CoinDTO();
        coinDTO.setCode(coinCode);
        coinDTO.setStatistic(statisticDTO);

        coinInfoDao.update(coinDTO);
        return coinInfoDao.getByCode(coinCode);
    }

    @Override
    public void getListCoins() {
        List<CoinName> listCoinNames = coinCrawler.getTopMarketCap();
        // filter stable coins
        List<CoinName> filteredCoins = listCoinNames.stream()
            .filter(coinName -> !coinName.getCode().contains("USD") && !coinName.getCode().contains("usd"))
            .collect(Collectors.toList());
        coinInfoDao.saveAll(CoinMapper.MAPPER.coinNamesToCoins(filteredCoins));
    }
}
