package net.cglcapital.coininfo.schedule;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.service.thirdparty.QuoteService;
import net.cglcapital.coininfo.common.db.dao.BreakoutLogDao;
import net.cglcapital.coininfo.common.db.domain.dto.BreakoutLogDTO;
import net.cglcapital.coininfo.common.model.MorningReport;
import net.cglcapital.coininfo.common.model.Quote;
import net.cglcapital.coininfo.service.thirdparty.SlackNotificationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.cglcapital.coininfo.common.constant.PeriodTime.*;

@Slf4j
@Component
public class MorningReportJob {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private SlackNotificationHelper slackNotificationHelper;
    @Autowired
    private BreakoutLogDao breakoutLogDao;

    @Scheduled(cron = "${cronjob.morning-report.cron-expression}", zone = "${cronjob.morning-report.zone}")
    public void morningReportNotification() {
        log.info("[MorningReportJob] - Executing morning report job...");
        // get random quote
        Quote quote = quoteService.getRandomQuote();

        // query 24h breakout logs
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyHoursBefore = now.minusHours(24);
        List<BreakoutLogDTO> breakoutLogs = breakoutLogDao.getBreakoutLogsFromTo(twentyHoursBefore, now);
        MorningReport morningReport = toMorningReport(breakoutLogs);

        log.info("Sending morning report notification...");
        slackNotificationHelper.sendMorningReportNotification(morningReport, quote);
    }

    private MorningReport toMorningReport(List<BreakoutLogDTO> breakoutLogs) {
        Map<String, List<String>> periodTimeBreakoutMap = new HashMap<>();
        for (BreakoutLogDTO breakoutLog : breakoutLogs) {
            String periodTime = breakoutLog.getPeriodTime();
            if (periodTimeBreakoutMap.containsKey(periodTime) &&
                !periodTimeBreakoutMap.get(periodTime).contains(breakoutLog.getCode())) {
                periodTimeBreakoutMap.get(periodTime).add(breakoutLog.getCode());
            } else {
                periodTimeBreakoutMap.put(periodTime, new ArrayList<>());
            }
        }

        return new MorningReport(
            periodTimeBreakoutMap.getOrDefault(ALL_TIME.name(), new ArrayList<>()),
            periodTimeBreakoutMap.getOrDefault(FIFTY_TWO_WEEKS.name(), new ArrayList<>()),
            periodTimeBreakoutMap.getOrDefault(NINETY_DAYS.name(), new ArrayList<>()),
            periodTimeBreakoutMap.getOrDefault(THIRTY_DAYS.name(), new ArrayList<>()),
            periodTimeBreakoutMap.getOrDefault(SEVEN_DAYS.name(), new ArrayList<>()));
    }
}
