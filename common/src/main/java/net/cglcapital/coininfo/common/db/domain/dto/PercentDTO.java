package net.cglcapital.coininfo.common.db.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PercentDTO implements Serializable {

    public String pair;

    public String exchange;

    public Float pricePeak;

    public Float priceTrough;

    public LocalDateTime peakDate;

    public LocalDateTime troughDate;

    public Float peakToTrough;

    public Float peakGrowth;

    public LocalDateTime createdDate;

    public LocalDateTime updatedDate;

    private String peakType;

    private Float growthBetweenCorrection;

    private Float previousTrough;

    private Integer timeBetweenPeak;

    private Integer timePeakTrough;

    private Integer timePreviousTroughToPeak;

    private Float buyTroughFibLvl;

    private Float takeProfitFibLvl;

    private Float fib236;

    private Float fib382;

    private Float fib500;

    private Float fib618;

    private Float fib786;

    private Float fibExt236;

    private Float fibExt382;

    private Float fibExt500;

    private Float fibExt618;

    private Float fibExt768;

    private Float fibExt1000;

    private Float fibExt1618;

    private Float fibExt2618;

    private Float fibExt4236;
}
