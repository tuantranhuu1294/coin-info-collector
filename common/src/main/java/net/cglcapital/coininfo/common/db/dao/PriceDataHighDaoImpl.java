package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PriceDataDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.TblPriceDataHigh;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPriceDataHigh;
import net.cglcapital.coininfo.common.db.mapper.PriceDataMapper;
import net.cglcapital.coininfo.common.db.repository.TblPriceDataHighRepository;
import net.cglcapital.coininfo.common.db.repository.ViewPriceDataHighRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PriceDataHighDaoImpl implements PriceDataHighDao {

    @Resource
    private ViewPriceDataHighRepository viewPriceDataHighRepository;

    @Resource
    private TblPriceDataHighRepository tblPriceDataHighRepository;

    @Override
    public List<PriceDataDTO> findAllView() {
        List<ViewPriceDataHigh> all = viewPriceDataHighRepository.findAll();
        return PriceDataMapper.MAPPER.jpaViewPriceDataHighToDTO(all);
    }

    @Transactional
    @Override
    public void saveAllTbl(List<PriceDataDTO> priceDataDTOList) {
        List<TblPriceDataHigh> tblPriceDataHighs = PriceDataMapper.MAPPER.dtoToJpaTblPriceDataHigh(priceDataDTOList);
        tblPriceDataHighRepository.saveAll(tblPriceDataHighs);
        log.info("Saved: {} Price Data High to database", tblPriceDataHighs.size());
    }

    @Transactional
    @Override
    public void deleteAllDataTbl() {
        tblPriceDataHighRepository.truncateTbl();
        log.info("Deleted: {} Price Data High");
    }
}
