package net.cglcapital.coininfo;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.util.CoinSymbolUtil;
import net.cglcapital.coininfo.service.BinanceWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@EnableAsync
@SpringBootApplication
public class StreamingApplication {

    @Autowired
    private BinanceWebSocketService binanceWebSocketService;
    @Autowired
    private CoinInfoDao coinInfoDao;

    public static void main(String[] args) {
        SpringApplication.run(StreamingApplication.class, args);
    }

    @PostConstruct
    public void run() {
        String coinSymbols = buildCoinSymbols();
        log.info("Listening coins: {}", coinSymbols);
        binanceWebSocketService.listenAggTradeEvent(coinSymbols);
    }

    private String buildCoinSymbols() {
        List<CoinDTO> coins = coinInfoDao.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < coins.size(); i++) {
            stringBuilder.append(CoinSymbolUtil.buildUsdtCoinPairSymbol(coins.get(i).getCode()).toLowerCase());
            if (i < coins.size() - 1) stringBuilder.append(",");
        }

        return stringBuilder.toString();
    }
}
