package net.cglcapital.coininfo.common.api.noti;

public interface NotificationService<T extends BaseNotiPayload> {

    void sendNotification(T payload);
}
