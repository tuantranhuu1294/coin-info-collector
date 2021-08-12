package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.mapper.BreakoutLogMapper;
import net.cglcapital.coininfo.common.db.domain.dto.BreakoutLogDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.BreakoutLog;
import net.cglcapital.coininfo.common.db.repository.BreakoutLogRepository;
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
public class BreakoutLogDaoImpl implements BreakoutLogDao {

    @Resource
    private BreakoutLogRepository breakoutLogRepository;


    @CacheEvict(value = "breakoutLog", condition = "#result == true", key = "#breakoutLogDTO.code + '_' + #breakoutLogDTO.periodTime")
    @Transactional
    @Override
    public boolean save(BreakoutLogDTO breakoutLogDTO) {
        BreakoutLog breakoutLog = BreakoutLogMapper.MAPPER.breakoutLogDTOToBreakoutLog(breakoutLogDTO);
        breakoutLogRepository.save(breakoutLog);
        return true;
    }


    @Cacheable(value = "breakoutLog", unless = "#result == null", key = "#coinCode + '_' + #periodTime")
    @Override
    public BreakoutLogDTO getLastBreakoutLog(String coinCode, String periodTime) {
        log.debug("Getting last breakout log of the coin: {} with period time: {}", coinCode, periodTime);
        BreakoutLog response = breakoutLogRepository.findFirstByCodeAndPeriodTimeOrderByBreakoutAtDesc(coinCode, periodTime);
        return BreakoutLogMapper.MAPPER.breakoutLogToBreakoutLogDTO(response);
    }

    @Override
    public List<BreakoutLogDTO> getBreakoutLogsFromTo(LocalDateTime from, LocalDateTime to) {
        List<BreakoutLog> breakoutLogs = breakoutLogRepository.findAllByBreakoutAtBetween(from, to);
        return BreakoutLogMapper.MAPPER.breakoutLogsToBreakoutLogDTOs(breakoutLogs);
    }
}
