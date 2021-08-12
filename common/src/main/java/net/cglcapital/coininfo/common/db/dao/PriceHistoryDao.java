package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;

import java.util.List;


public interface PriceHistoryDao {

    PriceHistoryDTO getLastDayPriceHistory(String coinCode);

    boolean save(PriceHistoryDTO priceHistoryDTO);

    void saveAll(List<PriceHistoryDTO> priceHistoryDTOList);
}
