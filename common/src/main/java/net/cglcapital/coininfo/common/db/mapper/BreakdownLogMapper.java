package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.BreakdownLogDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.BreakdownLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BreakdownLogMapper {

    BreakdownLogMapper MAPPER = Mappers.getMapper(BreakdownLogMapper.class);

    BreakdownLogDTO breakdownLogToBreakdownLogDTO(BreakdownLog breakdownLog);

    List<BreakdownLogDTO> breakdownLogsToBreakdownLogDTOs(List<BreakdownLog> breakdownLogList);

    BreakdownLog breakdownLogDTOToBreakdownLog(BreakdownLogDTO breakdownLogDTO);
}
