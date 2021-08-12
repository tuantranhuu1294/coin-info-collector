package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_price_data_stats")
public class TblPriceDataStats extends PriceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ATR14")
    public Float atr14;

    @Column(name = "ATR14_PERCENT")
    public Float atr14Percent;

    @Column(name = "ATR5")
    public Float atr5;

    @Column(name = "ATR5_PERCENT")
    public Float atr5Percent;

    @Column(name = "MA50")
    public Float ma50;

    @Column(name = "MA100")
    public Float ma100;

    @Column(name = "MA200")
    public Float ma200;

    @Column(name = "volume")
    public Float volume;

    @Column(name = "vol_a20")
    public Float volA20;

    @Column(name = "vol_cur_to_a20")
    public Float volCurToA20;

    @Column(name = "number_of_trades")
    public Float numberOfTrades;

    @Column(name = "trades_a20")
    public Float tradesA20;

    @Column(name = "trades_cur_to_a20")
    public Float tradesCurToA20;

    @Column(name = "created_date", insertable=false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
