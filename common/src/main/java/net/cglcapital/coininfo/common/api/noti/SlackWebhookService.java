package net.cglcapital.coininfo.common.api.noti;

import net.cglcapital.coininfo.common.model.slack.SlackMessagePayload;

public interface SlackWebhookService extends NotificationService<SlackMessagePayload> {

    void sendNotification(SlackMessagePayload payload, String webhookUrl);
}
