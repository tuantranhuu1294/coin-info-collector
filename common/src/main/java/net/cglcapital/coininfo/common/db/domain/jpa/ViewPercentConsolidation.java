package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "percent_consolidation")
public class ViewPercentConsolidation extends Percent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "peak_type")
    private String peakType;

}
