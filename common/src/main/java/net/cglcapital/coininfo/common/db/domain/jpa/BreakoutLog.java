package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "breakout_logs")
public class BreakoutLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "period_time")
    private String periodTime;

    @Column(name = "breakout_at")
    private LocalDateTime breakoutAt;
}
