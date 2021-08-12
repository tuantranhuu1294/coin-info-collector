package net.cglcapital.coininfo.common.db.domain.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class Percent {

    @Column(name = "pair")
    public String pair;

    @Column(name = "exchange")
    public String exchange;

    @Column(name = "price_peak")
    public Float pricePeak;

    @Column(name = "price_trough")
    public Float priceTrough;

    @Column(name = "peak_date")
    public LocalDateTime peakDate;

    @Column(name = "trough_date")
    public LocalDateTime troughDate;

    @Column(name = "peak_to_trough")
    public Float peakToTrough;

    @Column(name = "peak_growth")
    public Float peakGrowth;

}
