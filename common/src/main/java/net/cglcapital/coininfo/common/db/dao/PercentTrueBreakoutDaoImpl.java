package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentTrueBreakout;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentTrueBreakout;
import net.cglcapital.coininfo.common.db.mapper.PercentMapper;
import net.cglcapital.coininfo.common.db.repository.TblPercentTrueBreakoutRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPercentTrueBreakoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PercentTrueBreakoutDaoImpl implements PercentTrueBreakoutDao {

    @Resource
    private ViewPercentTrueBreakoutRepository viewPercentTrueBreakoutRepository;

    @Resource
    private TblPercentTrueBreakoutRepository tblPercentTrueBreakoutRepository;

    @Override
    public List<PercentDTO> findAllView() {
        List<ViewPercentTrueBreakout> all = viewPercentTrueBreakoutRepository.findAll();
        return PercentMapper.MAPPER.jpaViewPercentTrueBreakoutToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PercentDTO> percentDTOList) {
        List<TblPercentTrueBreakout> tblPercentFailedBreakouts = PercentMapper.MAPPER.dtoToJpaTblPercentTrueBreakout(percentDTOList);
        tblPercentTrueBreakoutRepository.saveAll(tblPercentFailedBreakouts);
        log.info("Saved: {} Percent True Breakout to database", percentDTOList.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPercentTrueBreakoutRepository.truncateTbl();
        log.info("Deleted: {} Percent True Breakout");
    }
}
