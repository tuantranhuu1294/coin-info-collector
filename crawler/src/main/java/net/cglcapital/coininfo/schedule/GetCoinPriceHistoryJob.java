package net.cglcapital.coininfo.schedule;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.dao.PriceHistoryDao;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;
import net.cglcapital.coininfo.common.util.CoinSymbolUtil;
import net.cglcapital.coininfo.service.CoinPriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GetCoinPriceHistoryJob {

    @Autowired
    private CoinPriceHistoryService coinPriceHistoryService;
    @Autowired
    private PriceHistoryDao priceHistoryDao;
    @Autowired
    private CoinInfoDao coinInfoDao;

    @Value("${crawler.binance.coin-price-history.limit-size-per-request}")
    private int limitSizePerReq;
    @Value("${crawler.binance.coin-price-history.limit-time-size-per-request}")
    private long limitTimeSizePerReq;

    @Scheduled(cron = "${cronjob.get-coin-price-history.cron-expression}", zone = "${cronjob.get-coin-price-history.zone}") // schedule daily at a specific time
    public void crawlCoinPriceHistory() {
        log.info("[GetCoinPriceHistoryJob] - Crawling price history of following coins");

        List<CoinDTO> coinsList = coinInfoDao.findAll();
        for (CoinDTO coinDTO : coinsList) {
            // getting last day record of this coin
            PriceHistoryDTO lastRecord = priceHistoryDao.getLastDayPriceHistory(coinDTO.getCode());
            if (lastRecord == null) {
                // crawl data from beginning
                crawlFromBeginning(coinDTO);
            } else {
                // crawl data from last record time to now
                crawlFromLastRecordToNow(lastRecord, coinDTO);
            }
            log.info("[GetCoinPriceHistoryJob] Getting price history of coin: {} completed", coinDTO.getCode());
        }
    }

    private void crawlFromBeginning(CoinDTO coinDTO) {
        long endTime = getCurrentDayAtStartTimeMilli();
        long startTime = endTime - limitTimeSizePerReq;

        boolean isDone = false;
        while (!isDone) {
            List<PriceHistoryDTO> records = getPriceHistory(coinDTO.getCode(), startTime, endTime);
            if (records == null || records.isEmpty()) {
                isDone = true;
                continue;
            } else {
                endTime = startTime - 1L;
                startTime = startTime - limitTimeSizePerReq;
            }

            // save to database
            log.debug("[GetCoinPriceHistoryJob] Store {} records to database", records.size());
            priceHistoryDao.saveAll(records);
        }
    }

    private void crawlFromLastRecordToNow(PriceHistoryDTO lastRecord, CoinDTO coinDTO) {
        final long currentTime = getCurrentDayAtStartTimeMilli();
        long startTime = lastRecord.getCloseTime().toInstant(ZoneOffset.UTC).toEpochMilli() + 1L;
        long endTime = Math.min(startTime + limitTimeSizePerReq, currentTime) - 1L;

        boolean isDone = false;
        while (!isDone) {
            List<PriceHistoryDTO> records = getPriceHistory(coinDTO.getCode(), startTime, endTime);
            if (records != null) {
                // save to database
                log.debug("[GetCoinPriceHistoryJob] Store {} records to database", records.size());
                priceHistoryDao.saveAll(records);
            } else {
                isDone = true;
                continue;
            }

            if (endTime + 1L < currentTime) {
                startTime = endTime;
                endTime = Math.min(endTime + limitTimeSizePerReq, currentTime) - 1L;
            } else {
                isDone = true;
                continue;
            }
        }
    }

    private List<PriceHistoryDTO> getPriceHistory(String coinCode, long startTime, long endTime) {
        try {
            List<PriceHistoryDTO> records = coinPriceHistoryService.getPriceHistory(CoinSymbolUtil.buildUsdtCoinPairSymbol(coinCode),
                limitSizePerReq, startTime, endTime);
            // set coin code for all records
            if (records != null) {
                records.forEach(priceHistoryDTO -> priceHistoryDTO.setCode(coinCode));
            }

            return records;
        } catch (Exception e) {
            log.error("[GetCoinPriceHistoryJob] Error when getting price history of coin: " + coinCode, e);
            return new ArrayList<>();
        }
    }

    private static long getCurrentDayAtStartTimeMilli() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDateTime startTime = today.atStartOfDay();
        return startTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
