package net.cglcapital.coininfo.common.db.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakdownLogDTO implements Serializable {

    private Integer id;

    private String code;

    private String periodTime;

    private LocalDateTime breakdownAt;
}
