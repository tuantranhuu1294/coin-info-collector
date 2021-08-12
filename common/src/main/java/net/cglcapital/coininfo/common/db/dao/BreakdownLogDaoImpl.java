package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.BreakdownLogDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.BreakdownLog;
import net.cglcapital.coininfo.common.db.mapper.BreakdownLogMapper;
import net.cglcapital.coininfo.common.db.repository.BreakdownLogRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
public class BreakdownLogDaoImpl implements BreakdownLogDao {

    @Resource
    private BreakdownLogRepository breakdownLogRepository;


    @CacheEvict(value = "breakdownLog", condition = "#result == true", key = "#breakdownLogDTO.code + '_' + #breakdownLogDTO.periodTime")
    @Transactional
    @Override
    public boolean save(BreakdownLogDTO breakdownLogDTO) {
        BreakdownLog breakdownLog = BreakdownLogMapper.MAPPER.breakdownLogDTOToBreakdownLog(breakdownLogDTO);
        breakdownLogRepository.save(breakdownLog);
        return true;
    }


    @Cacheable(value = "breakdownLog", unless = "#result == null", key = "#coinCode + '_' + #periodTime")
    @Override
    public BreakdownLogDTO getLastBreakdownLog(String coinCode, String periodTime) {
        log.debug("Getting last breakout log of the coin: {} with period time: {}", coinCode, periodTime);
        BreakdownLog response = breakdownLogRepository.findFirstByCodeAndPeriodTimeOrderByBreakdownAtDesc(coinCode, periodTime);
        return BreakdownLogMapper.MAPPER.breakdownLogToBreakdownLogDTO(response);
    }

    @Override
    public List<BreakdownLogDTO> getBreakdownLogsFromTo(LocalDateTime from, LocalDateTime to) {
        List<BreakdownLog> breakdownLogs = breakdownLogRepository.findAllByBreakdownAtBetween(from, to);
        return BreakdownLogMapper.MAPPER.breakdownLogsToBreakdownLogDTOs(breakdownLogs);
    }
}
