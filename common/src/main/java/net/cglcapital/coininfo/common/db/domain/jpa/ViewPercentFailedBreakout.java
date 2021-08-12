package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "percent_failed_breakout")
public class ViewPercentFailedBreakout extends Percent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "growth_between_correction")
    private Float growthBetweenCorrection;

    @Column(name = "peak_type")
    private String peakType;

}
