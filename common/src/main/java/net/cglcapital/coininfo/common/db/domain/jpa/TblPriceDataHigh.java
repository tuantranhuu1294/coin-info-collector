package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_price_data_high")
public class TblPriceDataHigh extends PriceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "high_last_20_days")
    public Float highLast20Days;

    @Column(name = "high_last_30_days")
    public Float highLast30Days;

    @Column(name = "high_last_40_days")
    public Float highLast40Days;

    @Column(name = "high_last_55_days")
    public Float highLast55Days;

    @Column(name = "high_last_70_days")
    public Float highLast70Days;

    @Column(name = "high_last_90_days")
    public Float highLast90Days;

    @Column(name = "high_ath")
    public Float highAth;

    @Column(name = "created_date", insertable=false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
