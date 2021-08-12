package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;

import java.util.List;

public interface PercentFailedBreakoutDao {

    List<PercentDTO> findAllView();

    void saveAllTbl(List<PercentDTO> percentDTOList);

    void deleteAllDataTbl();
}
