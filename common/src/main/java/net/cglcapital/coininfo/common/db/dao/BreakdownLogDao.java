package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.BreakdownLogDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BreakdownLogDao {

    boolean save(BreakdownLogDTO breakdownLogDTO);

    BreakdownLogDTO getLastBreakdownLog(String coinCode, String periodTime);

    List<BreakdownLogDTO> getBreakdownLogsFromTo(LocalDateTime from, LocalDateTime to);
}
