package net.cglcapital.coininfo.schedule;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.StatisticDTO;
import net.cglcapital.coininfo.common.db.mapper.StatisticMapper;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinCMCStatistic;
import net.cglcapital.coininfo.service.CoinCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GetCoinStatisticsJob {

    @Autowired
    private CoinCrawler coinCrawler;
    @Autowired
    private CoinInfoDao coinInfoDao;

    @Scheduled(cron = "${cronjob.get-coin-statistic.cron-expression}", zone = "${cronjob.get-coin-statistic.zone}") // schedule daily at a specific time
    public void crawlCoinPriceStatistic() {
        log.info("[ScheduleJob] - Crawling price statistic of following coins");

        List<CoinDTO> coinsList = findListCoins();
        for (CoinDTO coinDTO : coinsList) {
            log.info("Crawling price history of coin: {}", coinDTO.getName());
            CoinCMCStatistic coinCMCStatistic = coinCrawler.getCoinPriceStatistic(coinDTO.getCode(), coinDTO.getCmcUrlSymbol());
            StatisticDTO statisticDTO = StatisticMapper.MAPPER.priceHistoryToStatisticDTO(coinCMCStatistic);
            CoinDTO updateCoinDTO = new CoinDTO();
            updateCoinDTO.setCode(coinDTO.getCode());
            updateCoinDTO.setStatistic(statisticDTO);

            coinInfoDao.update(updateCoinDTO);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                // ignore this exception
            }
        }
    }

    private List<CoinDTO> findListCoins() {
        // Find list coin sorting by field "update_at" in table statistics, the old rows will be sorted on top
        Sort sort = Sort.by(Sort.Order.asc("statistic.updateAt"));
        return coinInfoDao.findAll(sort);
    }
}
