package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_price_data_low")
public class TblPriceDataLow extends PriceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "low_last_20_days")
    public Float lowLast20Days;

    @Column(name = "low_last_30_days")
    public Float lowLast30Days;

    @Column(name = "low_last_40_days")
    public Float lowLast40Days;

    @Column(name = "low_last_55_days")
    public Float lowLast55Days;

    @Column(name = "low_last_70_days")
    public Float lowLast70Days;

    @Column(name = "low_last_90_days")
    public Float lowLast90Days;

    @Column(name = "low_ath")
    public Float lowAth;

    @Column(name = "created_date", insertable=false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
