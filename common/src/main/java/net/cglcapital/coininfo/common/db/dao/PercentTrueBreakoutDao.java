package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;

import java.util.List;

public interface PercentTrueBreakoutDao {

    List<PercentDTO> findAllView();

    void saveAllTbl(List<PercentDTO> percentDTOList);

    void deleteAllDataTbl();
}
