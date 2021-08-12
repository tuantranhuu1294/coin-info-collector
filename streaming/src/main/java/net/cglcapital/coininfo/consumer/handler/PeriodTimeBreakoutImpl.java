package net.cglcapital.coininfo.consumer.handler;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.constant.PeriodTime;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.StatisticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PeriodTimeBreakoutImpl implements TimelineBreakout {

    @Autowired
    private CoinInfoDao coinInfoDao;

    @Override
    public int getFibonacciLevel(PeriodTime periodTime, String coinCode, float price) {
        //TODO: implement logic of flow response fibonacci level when price of the coin is at any fibonacci retracement level
        return -1;
    }

    @Override
    public boolean isBreakout(PeriodTime periodTime, String coinCode, float price) {
        CoinDTO coinDTO = coinInfoDao.getByCode(coinCode);
        if (coinDTO == null) {
            log.warn("[PeriodTimeBreakout] Coin: {} not found", coinCode);
            return false;
        }

        StatisticDTO coinStatistic = coinDTO.getStatistic();
        if (coinStatistic == null) {
            log.warn("[PeriodTimeBreakout] Price history of the coin: {} not found", coinCode);
            return false;
        }

        switch (periodTime) {
            case ALL_TIME:
                Float allTimeHighPrice = coinDTO.getStatistic().getAllTimeHigh();
                return allTimeHighPrice != null && price >= allTimeHighPrice;
            case FIFTY_TWO_WEEKS:
                Float fiftyTwoWeeksHighPrice = coinDTO.getStatistic().getFiftyTwoWeekHigh();
                return fiftyTwoWeeksHighPrice != null && price >= fiftyTwoWeeksHighPrice;
            case SEVEN_DAYS:
                Float sevenDaysHighPrice = coinDTO.getStatistic().getSevenDayHigh();
                return sevenDaysHighPrice != null && price >= sevenDaysHighPrice;
            case THIRTY_DAYS:
                Float thirtyDaysHighPrice = coinDTO.getStatistic().getThirtyDayHigh();
                return thirtyDaysHighPrice != null && price >= thirtyDaysHighPrice;
            case NINETY_DAYS:
                Float ninetyDaysHighPrice = coinDTO.getStatistic().getNinetyDayHigh();
                return ninetyDaysHighPrice != null && price >= ninetyDaysHighPrice;
            case TWENTY_FOUR_HOURS:
                Float twentyFourHoursHighPrice = coinDTO.getStatistic().getTwentyFourHourHigh();
                return twentyFourHoursHighPrice != null && price >= twentyFourHoursHighPrice;
            default:
                log.warn("Period time: {} has not handled yet", periodTime);
                return false;
        }
    }
}
