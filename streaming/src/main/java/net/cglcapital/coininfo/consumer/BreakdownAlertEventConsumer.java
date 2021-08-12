package net.cglcapital.coininfo.consumer;

import com.binance.api.client.domain.event.AggTradeEvent;
import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.constant.PeriodTime;
import net.cglcapital.coininfo.common.db.dao.BreakdownLogDao;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.domain.dto.BreakdownLogDTO;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.util.CoinSymbolUtil;
import net.cglcapital.coininfo.consumer.handler.PeriodTimeBreakdownImpl;
import net.cglcapital.coininfo.service.thirdparty.SlackNotificationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static net.cglcapital.coininfo.common.constant.PeriodTime.*;

@Slf4j
@Component
public class BreakdownAlertEventConsumer implements EventConsumer<AggTradeEvent> {

    @Autowired
    private PeriodTimeBreakdownImpl periodTimeBreakdownImpl;
    @Autowired
    private SlackNotificationHelper slackNotificationHelper;
    @Autowired
    private BreakdownLogDao breakdownLogDao;
    @Autowired
    private CoinInfoDao coinInfoDao;

    private final HashMap<String, AtomicInteger> eventCounterMap = new HashMap<>();

    @Override
    public void consume(AggTradeEvent event) {
        String coinCode = CoinSymbolUtil.extractCoinCode(event.getSymbol());
        float price = Float.parseFloat(event.getPrice());

        if (!isAcceptableEventCounter(coinCode)) return;

        log.debug("[BreakdownAlertEvent] Coin: {}, Price: {}", coinCode, price);
        if (!isInBreakdownList(ALL_TIME, coinCode) &&
            periodTimeBreakdownImpl.isBreakdown(ALL_TIME, coinCode, price)) {

            handleBreakdownFlow(ALL_TIME, coinCode, price);
            log.info("[BreakdownAlertEvent] Coin: {} is Breakdown all time trough", coinCode);
        } else if (!isInBreakdownList(FIFTY_TWO_WEEKS, coinCode) &&
            periodTimeBreakdownImpl.isBreakdown(FIFTY_TWO_WEEKS, coinCode, price)) {

            handleBreakdownFlow(FIFTY_TWO_WEEKS, coinCode, price);
            log.info("[BreakdownAlertEvent] Coin: {} is Breakdown 52w trough", coinCode);
        } else if (!isInBreakdownList(NINETY_DAYS, coinCode) &&
            periodTimeBreakdownImpl.isBreakdown(NINETY_DAYS, coinCode, price)) {

            handleBreakdownFlow(NINETY_DAYS, coinCode, price);
            log.info("[BreakdownAlertEvent] Coin: {} is Breakdown 90d trough", coinCode);
        } else if (!isInBreakdownList(THIRTY_DAYS, coinCode) &&
            periodTimeBreakdownImpl.isBreakdown(THIRTY_DAYS, coinCode, price)) {

            handleBreakdownFlow(THIRTY_DAYS, coinCode, price);
            log.info("[BreakdownAlertEvent] Coin: {} is Breakdown 30d trough", coinCode);
        } else if (!isInBreakdownList(SEVEN_DAYS, coinCode) &&
            periodTimeBreakdownImpl.isBreakdown(SEVEN_DAYS, coinCode, price)) {

            handleBreakdownFlow(SEVEN_DAYS, coinCode, price);
            log.info("[BreakdownAlertEvent] Coin: {} is Breakdown 7d trough", coinCode);
        } else if (!isInBreakdownList(TWENTY_FOUR_HOURS, coinCode) &&
            periodTimeBreakdownImpl.isBreakdown(TWENTY_FOUR_HOURS, coinCode, price)) {

            handleBreakdownFlow(TWENTY_FOUR_HOURS, coinCode, price);
            log.info("[BreakdownAlertEvent] Coin: {} is Breakdown 24h trough", coinCode);
        } else {
            log.debug("[BreakdownAlertEvent] Nothing happen!");
        }
    }

    private static final int NUM_INTERVAL_EVENTS = 700;

    private boolean isAcceptableEventCounter(String coinCode) {
        if (!eventCounterMap.containsKey(coinCode)) {
            eventCounterMap.put(coinCode, new AtomicInteger(0));
            return true;
        } else {
            int counter = eventCounterMap.get(coinCode).incrementAndGet();
            if (counter == NUM_INTERVAL_EVENTS) {
                eventCounterMap.put(coinCode, new AtomicInteger(0));
                return true;
            }
        }

        return false;
    }

    private static final int BREAKDOWN_LOCK_HOURS = 72; // 3 days

    private boolean isInBreakdownList(PeriodTime periodTime, String coinCode) {
        BreakdownLogDTO BreakdownLog = breakdownLogDao.getLastBreakdownLog(coinCode, periodTime.name());
        if (BreakdownLog == null) return false;
        else return BreakdownLog.getBreakdownAt().until(LocalDateTime.now(), ChronoUnit.HOURS) < BREAKDOWN_LOCK_HOURS;
    }

    private void handleBreakdownFlow(PeriodTime periodTime, String coinCode, float price) {
        // save new Breakdown log to database
        addBreakdownLog(periodTime, coinCode);

        if (periodTime.equals(SEVEN_DAYS) || periodTime.equals(TWENTY_FOUR_HOURS)) {
            log.info("[BreakdownAlertEvent] Ignore notification for Breakdown period time: 7d and 24h");
            return;
        }

        // push Breakdown noti
        CoinDTO coinDTO = coinInfoDao.getByCode(coinCode);
        slackNotificationHelper.sendBreakdownNotification(coinDTO, periodTime, price);
    }

    private void addBreakdownLog(PeriodTime periodTime, String coinCode) {
        if (periodTime.equals(ALL_TIME)) {
            breakdownLogDao.save(new BreakdownLogDTO(null, coinCode, ALL_TIME.name(), LocalDateTime.now()));
            addBreakdownLog(FIFTY_TWO_WEEKS, coinCode);
        } else if (periodTime.equals(FIFTY_TWO_WEEKS)) {
            breakdownLogDao.save(new BreakdownLogDTO(null, coinCode, FIFTY_TWO_WEEKS.name(), LocalDateTime.now()));
            addBreakdownLog(NINETY_DAYS, coinCode);
        } else if (periodTime.equals(NINETY_DAYS)) {
            breakdownLogDao.save(new BreakdownLogDTO(null, coinCode, NINETY_DAYS.name(), LocalDateTime.now()));
            addBreakdownLog(THIRTY_DAYS, coinCode);
        } else if (periodTime.equals(THIRTY_DAYS)) {
            breakdownLogDao.save(new BreakdownLogDTO(null, coinCode, THIRTY_DAYS.name(), LocalDateTime.now()));
            addBreakdownLog(SEVEN_DAYS, coinCode);
        } else if (periodTime.equals(SEVEN_DAYS)) {
            breakdownLogDao.save(new BreakdownLogDTO(null, coinCode, SEVEN_DAYS.name(), LocalDateTime.now()));
            addBreakdownLog(TWENTY_FOUR_HOURS, coinCode);
        } else if (periodTime.equals(TWENTY_FOUR_HOURS)) {
            breakdownLogDao.save(new BreakdownLogDTO(null, coinCode, TWENTY_FOUR_HOURS.name(), LocalDateTime.now()));
        }
    }
}
