package net.cglcapital.coininfo.common.db.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StatisticDTO implements Serializable {

    private Float twentyFourHourLow;

    private Float twentyFourHourHigh;

    private Integer marketRank;

    private BigDecimal marketCap;

    private Float sevenDayLow;

    private Float sevenDayHigh;

    private Float thirtyDayLow;

    private Float thirtyDayHigh;

    private Float ninetyDayLow;

    private Float ninetyDayHigh;

    private Float fiftyTwoWeekLow;

    private Float fiftyTwoWeekHigh;

    private Float allTimeLow;

    private Float allTimeHigh;
}
