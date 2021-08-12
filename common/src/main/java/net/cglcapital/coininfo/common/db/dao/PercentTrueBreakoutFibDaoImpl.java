package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentTrueBreakoutFib;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentTrueBreakoutFib;
import net.cglcapital.coininfo.common.db.mapper.PercentMapper;
import net.cglcapital.coininfo.common.db.repository.TblPercentTrueBreakoutFibRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPercentTrueBreakoutFibRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PercentTrueBreakoutFibDaoImpl implements PercentTrueBreakoutFibDao {

    @Resource
    private ViewPercentTrueBreakoutFibRepository viewPercentTrueBreakoutFibRepository;

    @Resource
    private TblPercentTrueBreakoutFibRepository tblPercentTrueBreakoutFibRepository;

    @Override
    public List<PercentDTO> findAllView() {
        List<ViewPercentTrueBreakoutFib> all = viewPercentTrueBreakoutFibRepository.findAll();
        return PercentMapper.MAPPER.jpaViewPercentTrueBreakoutFibToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PercentDTO> percentDTOList) {
        List<TblPercentTrueBreakoutFib> tblPercentTrueBreakoutFibs = PercentMapper.MAPPER.dtoToJpaTblPercentTrueBreakoutFib(percentDTOList);
        tblPercentTrueBreakoutFibRepository.saveAll(tblPercentTrueBreakoutFibs);
        log.info("Saved: {} Percent True Breakout Fib to database", percentDTOList.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPercentTrueBreakoutFibRepository.truncateTbl();
        log.info("Deleted: {} Percent True Breakout Fib");
    }
}
