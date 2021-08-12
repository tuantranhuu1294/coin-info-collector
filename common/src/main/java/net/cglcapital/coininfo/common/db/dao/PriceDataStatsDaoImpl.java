package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PriceDataDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPriceDataStats;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPriceDataStats;
import net.cglcapital.coininfo.common.db.mapper.PriceDataMapper;
import net.cglcapital.coininfo.common.db.repository.TblPriceDataStatsRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPriceDataStatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PriceDataStatsDaoImpl implements PriceDataStatsDao {

    @Resource
    private ViewPriceDataStatsRepository viewPriceDataStatsRepository;

    @Resource
    private TblPriceDataStatsRepository tblPriceDataStatsRepository;

    @Override
    public List<PriceDataDTO> findAllView() {
        List<ViewPriceDataStats> all = viewPriceDataStatsRepository.findAll();
        return PriceDataMapper.MAPPER.jpaViewPriceDataStatsToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PriceDataDTO> priceDataDTOList) {
        List<TblPriceDataStats> tblPriceDataStats = PriceDataMapper.MAPPER.dtoToJpaTblPriceDataStats(priceDataDTOList);
        tblPriceDataStatsRepository.saveAll(tblPriceDataStats);
        log.info("Saved: {} Price Data Stats to database", tblPriceDataStats.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPriceDataStatsRepository.truncateTbl();
        log.info("Deleted: {} Price Data Stats");
    }
}
