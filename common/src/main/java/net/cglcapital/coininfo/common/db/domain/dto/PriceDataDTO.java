package net.cglcapital.coininfo.common.db.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PriceDataDTO implements Serializable {

    public String pair;

    public String exchange;

    public Float open;

    public Float high;
    public Float low;
    public Float close;
    public LocalDateTime openTime;
    public Float highLast20Days;
    public Float highLast30Days;
    public Float highLast40Days;
    public Float highLast55Days;
    public Float highLast70Days;
    public Float highLast90Days;
    public Float highAth;

    public Float lowLast20Days;
    public Float lowLast30Days;
    public Float lowLast40Days;
    public Float lowLast55Days;
    public Float lowLast70Days;
    public Float lowLast90Days;
    public Float lowAth;

    public Float atr14;
    public Float atr14Percent;
    public Float atr5;
    public Float atr5Percent;
    public Float ma50;
    public Float ma100;
    public Float ma200;
    public Float volume;
    public Float volA20;
    public Float volCurToA20;
    public Float numberOfTrades;
    public Float tradesA20;
    public Float tradesCurToA20;

    public LocalDateTime createdDate;

    public LocalDateTime updatedDate;

}
