package net.cglcapital.coininfo.common.model.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoinCMCStatistic {

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
