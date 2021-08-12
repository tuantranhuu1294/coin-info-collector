package net.cglcapital.coininfo.common.db.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PriceHistoryDTO implements Serializable {

    private String code;

    private Float openPrice;

    private Float closePrice;

    private Float highPrice;

    private Float lowPrice;

    private Float volume;

    private Long numberOfTrades;

    private Float quoteAssetVolume;

    private Float takerBuyQuoteAssetVolume;

    private Float takerBuyBaseAssetVolume;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;
}
