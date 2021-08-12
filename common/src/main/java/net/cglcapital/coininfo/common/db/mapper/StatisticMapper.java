package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.StatisticDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.Statistic;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinCMCStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StatisticMapper {

    StatisticMapper MAPPER = Mappers.getMapper(StatisticMapper.class);

    StatisticDTO statisticToStatisticDTO(Statistic statistic);

    Statistic statisticDTOToStatistic(StatisticDTO statisticDTO);

    StatisticDTO priceHistoryToStatisticDTO(CoinCMCStatistic coinCMCStatistic);

}
