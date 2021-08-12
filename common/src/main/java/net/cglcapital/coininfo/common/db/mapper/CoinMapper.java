package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.Coin;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinName;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CoinMapper {

    CoinMapper MAPPER = Mappers.getMapper(CoinMapper.class);

    CoinDTO coinToCoinDTO(Coin coin);

    List<CoinDTO> coinsToCoinDTOs(List<Coin> coins);

    Coin coinDTOToCoin(CoinDTO coinDTO);

    CoinDTO coinNameToCoin(CoinName coinName);

    List<CoinDTO> coinNamesToCoins(List<CoinName> coinName);

    List<Coin> coinDTOsToCoins(List<CoinDTO> coinDTOList);
}
