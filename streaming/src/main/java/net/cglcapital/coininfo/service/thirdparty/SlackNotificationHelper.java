package net.cglcapital.coininfo.service.thirdparty;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.noti.SlackWebhookService;
import net.cglcapital.coininfo.common.constant.PeriodTime;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.model.MorningReport;
import net.cglcapital.coininfo.common.model.Quote;
import net.cglcapital.coininfo.common.model.slack.SlackMessagePayload;
import net.cglcapital.coininfo.common.model.slack.SlackNotificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlackNotificationHelper {

    @Value("${service.slack.webhook.channel.breakout-alert-url}")
    private String channelBreakoutAlertUrl;

    @Value("${service.slack.webhook.channel.breakdown-alert-url}")
    private String channelBreakdownAlertUrl;

    @Autowired
    private SlackWebhookService slackWebhookService;

    @Async
    public void sendBreakoutNotification(CoinDTO coinDTO, PeriodTime periodTime, float currentPrice) {
        log.info("Sending breakout notification...");
        SlackMessagePayload payload = SlackNotificationBuilder.buildBreakoutMessage(coinDTO, periodTime, currentPrice);
        slackWebhookService.sendNotification(payload, channelBreakoutAlertUrl);
    }

    @Async
    public void sendBreakdownNotification(CoinDTO coinDTO, PeriodTime periodTime, float currentPrice) {
        log.info("Sending breakdown notification...");
        SlackMessagePayload payload = SlackNotificationBuilder.buildBreakdownMessage(coinDTO, periodTime, currentPrice);
        slackWebhookService.sendNotification(payload, channelBreakdownAlertUrl);
    }

    @Async
    public void sendMorningReportNotification(MorningReport morningReport, Quote quote) {
        SlackMessagePayload payload = SlackNotificationBuilder.buildMorningReportMessage(morningReport, quote);
        slackWebhookService.sendNotification(payload, channelBreakoutAlertUrl);
    }
}

