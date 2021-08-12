package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "price_histories")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "open_price")
    private Float openPrice;

    @Column(name = "close_price")
    private Float closePrice;

    @Column(name = "high_price")
    private Float highPrice;

    @Column(name = "low_price")
    private Float lowPrice;

    @Column(name = "volume")
    private Float volume;

    @Column(name = "number_of_trades")
    private Long numberOfTrades;

    @Column(name = "quote_asset_volume")
    private Float quoteAssetVolume;

    @Column(name = "taker_buy_quote_asset_volume")
    private Float takerBuyQuoteAssetVolume;

    @Column(name = "taker_buy_base_asset_volume")
    private Float takerBuyBaseAssetVolume;

    @Column(name = "open_time")
    private LocalDateTime openTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updateAt;
}
