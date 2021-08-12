package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_percent_failed_breakout")
public class TblPercentFailedBreakout extends Percent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "growth_between_correction")
    private Float growthBetweenCorrection;

    @Column(name = "peak_type")
    private String peakType;

    @Column(name = "created_date", insertable=false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
