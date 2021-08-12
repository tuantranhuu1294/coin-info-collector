package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "percent_true_breakout")
public class ViewPercentTrueBreakout extends Percent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "growth_between_correction")
    private Float growthBetweenCorrection;

    @Column(name = "previous_trough")
    private Float previousTrough;

    @Column(name = "peak_type")
    private String peakType;

}
