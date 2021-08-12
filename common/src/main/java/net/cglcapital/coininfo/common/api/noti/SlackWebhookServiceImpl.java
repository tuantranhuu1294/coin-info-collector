package net.cglcapital.coininfo.common.api.noti;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.thirdparty.BaseGateway;
import net.cglcapital.coininfo.common.model.slack.SlackMessagePayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SlackWebhookServiceImpl extends BaseGateway implements SlackWebhookService {

    @Value("${service.slack.webhook.channel.breakout-alert-url}")
    private String defaultWebhookUrl;

    @Override
    public void sendNotification(SlackMessagePayload payload) {
        log.info("[SlackWebhookService] - Posting a message to Slack channel...");
        jsonExchange(defaultWebhookUrl, HttpMethod.POST, prepareHeaders(), payload, String.class);
    }

    @Override
    public void sendNotification(SlackMessagePayload payload, String webhookUrl) {
        log.info("[SlackWebhookService] - Posting a message to Slack channel...");
        jsonExchange(webhookUrl, HttpMethod.POST, prepareHeaders(), payload, String.class);
    }

    @Override
    protected String serviceName() {
        return "SlackWebhookService";
    }
}
