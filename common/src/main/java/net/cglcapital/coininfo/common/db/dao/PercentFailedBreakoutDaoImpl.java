package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentFailedBreakout;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentFailedBreakout;
import net.cglcapital.coininfo.common.db.mapper.PercentMapper;
import net.cglcapital.coininfo.common.db.repository.TblPercentFailedBreakoutRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPercentFailedBreakoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PercentFailedBreakoutDaoImpl implements PercentFailedBreakoutDao {

    @Resource
    private ViewPercentFailedBreakoutRepository viewPercentFailedBreakoutRepository;

    @Resource
    private TblPercentFailedBreakoutRepository tblPercentFailedBreakoutRepository;

    @Override
    public List<PercentDTO> findAllView() {
        List<ViewPercentFailedBreakout> all = viewPercentFailedBreakoutRepository.findAll();
        return PercentMapper.MAPPER.jpaViewPercentFailedBreakoutToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PercentDTO> percentDTOList) {
        List<TblPercentFailedBreakout> tblPercentFailedBreakouts = PercentMapper.MAPPER.dtoToJpaTblPercentFailedBreakout(percentDTOList);
        tblPercentFailedBreakoutRepository.saveAll(tblPercentFailedBreakouts);
        log.info("Saved: {} Percent Failed Breakout to database", percentDTOList.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPercentFailedBreakoutRepository.truncateTbl();
        log.info("Deleted: {} Percent Failed Breakout");
    }
}
