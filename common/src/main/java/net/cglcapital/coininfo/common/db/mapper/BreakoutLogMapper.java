package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.BreakoutLogDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.BreakoutLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BreakoutLogMapper {

    BreakoutLogMapper MAPPER = Mappers.getMapper(BreakoutLogMapper.class);

    BreakoutLogDTO breakoutLogToBreakoutLogDTO(BreakoutLog breakoutLog);

    List<BreakoutLogDTO> breakoutLogsToBreakoutLogDTOs(List<BreakoutLog> breakoutLogList);

    BreakoutLog breakoutLogDTOToBreakoutLog(BreakoutLogDTO breakoutLogDTO);
}
