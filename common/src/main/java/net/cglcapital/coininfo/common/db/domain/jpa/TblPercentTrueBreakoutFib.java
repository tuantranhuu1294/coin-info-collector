package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_percent_true_breakout_fib")
public class TblPercentTrueBreakoutFib extends Percent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "previous_trough")
    private Float previousTrough;

    @Column(name = "time_between_peak")
    private Integer timeBetweenPeak;

    @Column(name = "time_peak_trough")
    private Integer timePeakTrough;

    @Column(name = "time_previous_trough_to_peak")
    private Integer timePreviousTroughToPeak;

    @Column(name = "buy_trough_fib_lvl")
    private Float buyTroughFibLvl;

    @Column(name = "take_profit_fib_lvl")
    private Float takeProfitFibLvl;

    @Column(name = "fib_236")
    private Float fib236;

    @Column(name = "fib_382")
    private Float fib382;

    @Column(name = "fib_500")
    private Float fib500;

    @Column(name = "fib_618")
    private Float fib618;

    @Column(name = "fib_786")
    private Float fib786;

    @Column(name = "fib_ext_236")
    private Float fibExt236;

    @Column(name = "fib_ext_382")
    private Float fibExt382;

    @Column(name = "fib_ext_500")
    private Float fibExt500;

    @Column(name = "fib_ext_618")
    private Float fibExt618;

    @Column(name = "fib_ext_768")
    private Float fibExt768;

    @Column(name = "fib_ext_1000")
    private Float fibExt1000;

    @Column(name = "fib_ext_1618")
    private Float fibExt1618;

    @Column(name = "fib_ext_2618")
    private Float fibExt2618;

    @Column(name = "fib_ext_4236")
    private Float fibExt4236;

    @Column(name = "created_date", insertable=false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
