package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.PriceHistory;
import net.cglcapital.coininfo.common.db.mapper.PriceHistoryMapper;
import net.cglcapital.coininfo.common.db.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PriceHistoryDaoImpl implements PriceHistoryDao {

    @Resource
    private PriceHistoryRepository priceHistoryRepository;

    @Override
    public PriceHistoryDTO getLastDayPriceHistory(String coinCode) {
        Optional<PriceHistory> recordOpt = priceHistoryRepository.findFirstByCodeOrderByOpenTimeDesc(coinCode);
        return recordOpt.map(PriceHistoryMapper.MAPPER::priceHistoryToPriceHistoryDTO).orElse(null);
    }

    @Transactional
    @Override
    public boolean save(PriceHistoryDTO priceHistoryDTO) {
        Objects.requireNonNull(priceHistoryDTO);

        priceHistoryRepository.save(PriceHistoryMapper.MAPPER.priceHistoryDTOToPriceHistory(priceHistoryDTO));
        return true;
    }

    @Transactional
    @Override
    public void saveAll(List<PriceHistoryDTO> priceHistoryDTOList) {
        List<PriceHistory> priceHistoryList = PriceHistoryMapper.MAPPER.listCoinDayPriceDTOToListPriceHistory(priceHistoryDTOList);
        priceHistoryRepository.saveAll(priceHistoryList);

        log.info("Saved: {} coins to database", priceHistoryDTOList.size());
    }
}
