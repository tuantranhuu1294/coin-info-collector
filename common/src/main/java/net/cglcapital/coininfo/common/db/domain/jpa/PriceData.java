package net.cglcapital.coininfo.common.db.domain.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class PriceData {

    @Column(name = "pair")
    public String pair;

    @Column(name = "exchange")
    public String exchange;

    @Column(name = "open")
    public Float open;

    @Column(name = "high")
    public Float high;

    @Column(name = "low")
    public Float low;

    @Column(name = "close")
    public Float close;

    @Column(name = "open_time")
    public LocalDateTime openTime;

}
