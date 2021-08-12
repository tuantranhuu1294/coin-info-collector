package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PriceDataDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPriceDataLow;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPriceDataLow;
import net.cglcapital.coininfo.common.db.mapper.PriceDataMapper;
import net.cglcapital.coininfo.common.db.repository.TblPriceDataLowRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPriceDataLowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PriceDataLowDaoImpl implements PriceDataLowDao {

    @Resource
    private ViewPriceDataLowRepository viewPriceDataLowRepository;

    @Resource
    private TblPriceDataLowRepository tblPriceDataLowRepository;

    @Override
    public List<PriceDataDTO> findAllView() {
        List<ViewPriceDataLow> all = viewPriceDataLowRepository.findAll();
        return PriceDataMapper.MAPPER.jpaViewPriceDataLowToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PriceDataDTO> priceDataDTOList) {
        List<TblPriceDataLow> tblPriceDataLows = PriceDataMapper.MAPPER.dtoToJpaTblPriceDataLow(priceDataDTOList);
        tblPriceDataLowRepository.saveAll(tblPriceDataLows);
        log.info("Saved: {} Price Data Low to database", tblPriceDataLows.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPriceDataLowRepository.truncateTbl();
        log.info("Deleted: {} Price Data Low");
    }
}
