package net.cglcapital.coininfo.mapper;

import com.binance.api.client.domain.market.Candlestick;
import net.cglcapital.coininfo.common.db.domain.dto.PriceHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(imports = {LocalDateTime.class, Instant.class, ZoneOffset.class})
public interface ResponseMapper {

    ResponseMapper MAPPER = Mappers.getMapper(ResponseMapper.class);

    @Mappings({
        @Mapping(target = "openTime", expression = "java(LocalDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getOpenTime()), ZoneOffset.UTC))"),
        @Mapping(target = "closeTime", expression = "java(LocalDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getCloseTime()), ZoneOffset.UTC))"),
        @Mapping(target = "openPrice", source = "open"),
        @Mapping(target = "closePrice", source = "close"),
        @Mapping(target = "highPrice", source = "high"),
        @Mapping(target = "lowPrice", source = "low"),
    })
    PriceHistoryDTO candlestickToPriceHistoryDTO(Candlestick candlestick);

    List<PriceHistoryDTO> candlesticksToPriceHistoryDTOs(List<Candlestick> candlesticks);
}
