package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "breakdown_logs")
public class BreakdownLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "period_time")
    private String periodTime;

    @Column(name = "breakdown_at")
    private LocalDateTime breakdownAt;
}
