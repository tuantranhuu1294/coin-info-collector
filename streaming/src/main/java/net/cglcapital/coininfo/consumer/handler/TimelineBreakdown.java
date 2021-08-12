package net.cglcapital.coininfo.consumer.handler;

import net.cglcapital.coininfo.common.constant.PeriodTime;

public interface TimelineBreakdown {

    /**
     * Return fibonacci level. It's 1 (1%), 2 (2%), 3 (3%), 5 (5%), 8 (8%)
     *
     * @param coinCode code of coin, example btc for bitcoin
     * @return 1, 2, 3, 5, 8 and -1 if out of bound
     */
    int getFibonacciLevel(PeriodTime periodTime, String coinCode, float price);

    /**
     * Checking if a crypto currency is lower than trough price of this timeline
     *
     * @param coinCode code of coin, example btc for bitcoin
     * @return true if current price is lower than trough price of this timeline
     */
    boolean isBreakdown(PeriodTime periodTime, String coinCode, float price);
}
