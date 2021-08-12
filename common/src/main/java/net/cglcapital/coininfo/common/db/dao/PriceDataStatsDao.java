package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.PriceDataDTO;

import java.util.List;

public interface PriceDataStatsDao {

    List<PriceDataDTO> findAllView();

    void saveAllTbl(List<PriceDataDTO> priceDataDTOList);

    void deleteAllDataTbl();
}
