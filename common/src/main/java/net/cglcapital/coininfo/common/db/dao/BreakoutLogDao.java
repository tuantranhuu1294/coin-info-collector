package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.BreakoutLogDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BreakoutLogDao {

    boolean save(BreakoutLogDTO breakoutLogDTO);

    BreakoutLogDTO getLastBreakoutLog(String coinCode, String periodTime);

    List<BreakoutLogDTO> getBreakoutLogsFromTo(LocalDateTime from, LocalDateTime to);
}
