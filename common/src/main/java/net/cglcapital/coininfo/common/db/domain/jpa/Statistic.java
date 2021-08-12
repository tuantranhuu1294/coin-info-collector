package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "statistics")
public class Statistic {

    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "code")
    private Coin coin;

    @Column(name = "twenty_four_hour_low")
    private Float twentyFourHourLow;

    @Column(name = "twenty_four_hour_high")
    private Float twentyFourHourHigh;

    @Column(name = "market_rank")
    private Integer marketRank;

    @Column(name = "market_cap")
    private BigDecimal marketCap;

    @Column(name = "seven_day_low")
    private Float sevenDayLow;

    @Column(name = "seven_day_high")
    private Float sevenDayHigh;

    @Column(name = "thirty_day_low")
    private Float thirtyDayLow;

    @Column(name = "thirty_day_high")
    private Float thirtyDayHigh;

    @Column(name = "ninety_day_low")
    private Float ninetyDayLow;

    @Column(name = "ninety_day_high")
    private Float ninetyDayHigh;

    @Column(name = "fifty_two_week_low")
    private Float fiftyTwoWeekLow;

    @Column(name = "fifty_two_week_high")
    private Float fiftyTwoWeekHigh;

    @Column(name = "all_time_high")
    private Float allTimeHigh;

    @Column(name = "all_time_low")
    private Float allTimeLow;

    @Column(name = "create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updateAt;
}
