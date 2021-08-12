package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentConsolidation;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentConsolidation;
import net.cglcapital.coininfo.common.db.mapper.PercentMapper;
import net.cglcapital.coininfo.common.db.repository.TblPercentConsolidationRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPercentConsolidationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PercentConsolidationDaoImpl implements PercentConsolidationDao{

    @Resource
    private ViewPercentConsolidationRepository viewPercentConsolidationRepository;

    @Resource
    private TblPercentConsolidationRepository tblPercentConsolidationRepository;

    @Override
    public List<PercentDTO> findAllView() {
        List<ViewPercentConsolidation> all = viewPercentConsolidationRepository.findAll();
        return PercentMapper.MAPPER.jpaViewPercentConsolidationToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PercentDTO> percentConsolidationDTOList) {
        List<TblPercentConsolidation> tblPercentConsolidations = PercentMapper.MAPPER.dtoToJpaTblPercentConsolidation(percentConsolidationDTOList);
        tblPercentConsolidationRepository.saveAll(tblPercentConsolidations);
        log.info("Saved: {} Percent Consolidation to database", percentConsolidationDTOList.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPercentConsolidationRepository.truncateTbl();
        log.info("Deleted: {} Percent Consolidation");
    }
}
