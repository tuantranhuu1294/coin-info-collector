package net.cglcapital.coininfo.consumer;

import com.binance.api.client.domain.event.AggTradeEvent;
import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.constant.PeriodTime;
import net.cglcapital.coininfo.common.db.dao.BreakoutLogDao;
import net.cglcapital.coininfo.common.db.dao.CoinInfoDao;
import net.cglcapital.coininfo.common.db.domain.dto.BreakoutLogDTO;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.util.CoinSymbolUtil;
import net.cglcapital.coininfo.consumer.handler.PeriodTimeBreakoutImpl;
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
public class BreakoutAlertEventConsumer implements EventConsumer<AggTradeEvent> {

    @Autowired
    private PeriodTimeBreakoutImpl periodTimeBreakoutImpl;
    @Autowired
    private SlackNotificationHelper slackNotificationHelper;
    @Autowired
    private BreakoutLogDao breakoutLogDao;
    @Autowired
    private CoinInfoDao coinInfoDao;

    private final HashMap<String, AtomicInteger> eventCounterMap = new HashMap<>();

    @Override
    public void consume(AggTradeEvent event) {
        String coinCode = CoinSymbolUtil.extractCoinCode(event.getSymbol());
        float price = Float.parseFloat(event.getPrice());

        if (!isAcceptableEventCounter(coinCode)) return;

        log.debug("[BreakoutAlertEventConsumer] Coin: {}, Price: {}", coinCode, price);
        if (!isInBreakoutList(ALL_TIME, coinCode) &&
            periodTimeBreakoutImpl.isBreakout(ALL_TIME, coinCode, price)) {

            handleBreakoutFlow(ALL_TIME, coinCode, price);
            log.info("[BreakoutAlertEventConsumer] Coin: {} is breakout all time peak", coinCode);
        } else if (!isInBreakoutList(FIFTY_TWO_WEEKS, coinCode) &&
            periodTimeBreakoutImpl.isBreakout(FIFTY_TWO_WEEKS, coinCode, price)) {

            handleBreakoutFlow(FIFTY_TWO_WEEKS, coinCode, price);
            log.info("[BreakoutAlertEventConsumer] Coin: {} is breakout 52w peak", coinCode);
        } else if (!isInBreakoutList(NINETY_DAYS, coinCode) &&
            periodTimeBreakoutImpl.isBreakout(NINETY_DAYS, coinCode, price)) {

            handleBreakoutFlow(NINETY_DAYS, coinCode, price);
            log.info("[BreakoutAlertEventConsumer] Coin: {} is breakout 90d peak", coinCode);
        } else if (!isInBreakoutList(THIRTY_DAYS, coinCode) &&
            periodTimeBreakoutImpl.isBreakout(THIRTY_DAYS, coinCode, price)) {

            handleBreakoutFlow(THIRTY_DAYS, coinCode, price);
            log.info("[BreakoutAlertEventConsumer] Coin: {} is breakout 30d peak", coinCode);
        } else if (!isInBreakoutList(SEVEN_DAYS, coinCode) &&
            periodTimeBreakoutImpl.isBreakout(SEVEN_DAYS, coinCode, price)) {

            handleBreakoutFlow(SEVEN_DAYS, coinCode, price);
            log.info("[BreakoutAlertEventConsumer] Coin: {} is breakout 7d peak", coinCode);
        } else if (!isInBreakoutList(TWENTY_FOUR_HOURS, coinCode) &&
            periodTimeBreakoutImpl.isBreakout(TWENTY_FOUR_HOURS, coinCode, price)) {

            handleBreakoutFlow(TWENTY_FOUR_HOURS, coinCode, price);
            log.info("[BreakoutAlertEventConsumer] Coin: {} is breakout 24h peak", coinCode);
        } else {
            log.debug("[BreakoutAlertEventConsumer] Nothing happen!");
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

    private static final int BREAKOUT_LOCK_HOURS = 72; // 3 days

    private boolean isInBreakoutList(PeriodTime periodTime, String coinCode) {
        BreakoutLogDTO breakoutLog = breakoutLogDao.getLastBreakoutLog(coinCode, periodTime.name());
        if (breakoutLog == null) return false;
        else return breakoutLog.getBreakoutAt().until(LocalDateTime.now(), ChronoUnit.HOURS) < BREAKOUT_LOCK_HOURS;
    }

    private void handleBreakoutFlow(PeriodTime periodTime, String coinCode, float price) {
        // save new breakout log to database
        addBreakoutLog(periodTime, coinCode);

        if (periodTime.equals(SEVEN_DAYS) || periodTime.equals(TWENTY_FOUR_HOURS)) {
            log.info("[BreakoutAlertEventConsumer] Ignore notification for breakout period time: 7d and 24h");
            return;
        }

        // push breakout noti
        CoinDTO coinDTO = coinInfoDao.getByCode(coinCode);
        slackNotificationHelper.sendBreakoutNotification(coinDTO, periodTime, price);
    }

    private void addBreakoutLog(PeriodTime periodTime, String coinCode) {
        if (periodTime.equals(ALL_TIME)) {
            breakoutLogDao.save(new BreakoutLogDTO(null, coinCode, ALL_TIME.name(), LocalDateTime.now()));
            addBreakoutLog(FIFTY_TWO_WEEKS, coinCode);
        } else if (periodTime.equals(FIFTY_TWO_WEEKS)) {
            breakoutLogDao.save(new BreakoutLogDTO(null, coinCode, FIFTY_TWO_WEEKS.name(), LocalDateTime.now()));
            addBreakoutLog(NINETY_DAYS, coinCode);
        } else if (periodTime.equals(NINETY_DAYS)) {
            breakoutLogDao.save(new BreakoutLogDTO(null, coinCode, NINETY_DAYS.name(), LocalDateTime.now()));
            addBreakoutLog(THIRTY_DAYS, coinCode);
        } else if (periodTime.equals(THIRTY_DAYS)) {
            breakoutLogDao.save(new BreakoutLogDTO(null, coinCode, THIRTY_DAYS.name(), LocalDateTime.now()));
            addBreakoutLog(SEVEN_DAYS, coinCode);
        } else if (periodTime.equals(SEVEN_DAYS)) {
            breakoutLogDao.save(new BreakoutLogDTO(null, coinCode, SEVEN_DAYS.name(), LocalDateTime.now()));
            addBreakoutLog(TWENTY_FOUR_HOURS, coinCode);
        } else if (periodTime.equals(TWENTY_FOUR_HOURS)) {
            breakoutLogDao.save(new BreakoutLogDTO(null, coinCode, TWENTY_FOUR_HOURS.name(), LocalDateTime.now()));
        }
    }

}
