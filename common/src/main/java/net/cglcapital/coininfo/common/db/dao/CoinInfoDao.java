package net.cglcapital.coininfo.common.db.dao;

import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CoinInfoDao {

    List<CoinDTO> findAll();

    List<CoinDTO> findAll(Sort sort);

    CoinDTO getByCode(String code);

    CoinDTO save(CoinDTO coinDTO);

    void saveAll(List<CoinDTO> coinDTOList);

    void update(CoinDTO coinDTO);
}
