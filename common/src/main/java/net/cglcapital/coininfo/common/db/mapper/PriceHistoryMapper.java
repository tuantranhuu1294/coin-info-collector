package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.PriceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PriceHistoryMapper {

    PriceHistoryMapper MAPPER = Mappers.getMapper(PriceHistoryMapper.class);

    PriceHistoryDTO priceHistoryToPriceHistoryDTO(PriceHistory PriceHistory);

    PriceHistory priceHistoryDTOToPriceHistory(PriceHistoryDTO PriceHistoryDTO);

    List<PriceHistory> listCoinDayPriceDTOToListPriceHistory(List<PriceHistoryDTO> priceHistoryDTOs);
}
