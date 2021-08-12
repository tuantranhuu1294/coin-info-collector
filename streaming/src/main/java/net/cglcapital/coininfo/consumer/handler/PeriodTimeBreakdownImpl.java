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
public class PeriodTimeBreakdownImpl implements TimelineBreakdown {

    @Autowired
    private CoinInfoDao coinInfoDao;

    @Override
    public int getFibonacciLevel(PeriodTime periodTime, String coinCode, float price) {
        //TODO: implement logic of flow response fibonacci level when price of the coin is at any fibonacci retracement level
        return -1;
    }

    @Override
    public boolean isBreakdown(PeriodTime periodTime, String coinCode, float price) {
        CoinDTO coinDTO = coinInfoDao.getByCode(coinCode);
        if (coinDTO == null) {
            log.warn("[PeriodTimeBreakdown] Coin: {} not found", coinCode);
            return false;
        }

        StatisticDTO coinStatistic = coinDTO.getStatistic();
        if (coinStatistic == null) {
            log.warn("[PeriodTimeBreakdown] Price history of the coin: {} not found", coinCode);
            return false;
        }

        switch (periodTime) {
            case ALL_TIME:
                Float allTimeLowPrice = coinDTO.getStatistic().getAllTimeLow();
                return allTimeLowPrice != null && price <= allTimeLowPrice;
            case FIFTY_TWO_WEEKS:
                Float fiftyTwoWeeksLowPrice = coinDTO.getStatistic().getFiftyTwoWeekLow();
                return fiftyTwoWeeksLowPrice != null && price <= fiftyTwoWeeksLowPrice;
            case SEVEN_DAYS:
                Float sevenDaysLowPrice = coinDTO.getStatistic().getSevenDayLow();
                return sevenDaysLowPrice != null && price <= sevenDaysLowPrice;
            case THIRTY_DAYS:
                Float thirtyDaysLowPrice = coinDTO.getStatistic().getThirtyDayLow();
                return thirtyDaysLowPrice != null && price <= thirtyDaysLowPrice;
            case NINETY_DAYS:
                Float ninetyDaysLowPrice = coinDTO.getStatistic().getNinetyDayLow();
                return ninetyDaysLowPrice != null && price <= ninetyDaysLowPrice;
            case TWENTY_FOUR_HOURS:
                Float twentyFourHoursLowPrice = coinDTO.getStatistic().getTwentyFourHourLow();
                return twentyFourHoursLowPrice != null && price <= twentyFourHoursLowPrice;
            default:
                log.warn("Period time: {} has not handled yet", periodTime);
                return false;
        }
    }
}
