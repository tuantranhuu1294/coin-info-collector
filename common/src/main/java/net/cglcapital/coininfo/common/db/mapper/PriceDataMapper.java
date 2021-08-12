package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.PriceDataDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PriceDataMapper {

    PriceDataMapper MAPPER = Mappers.getMapper(PriceDataMapper.class);

    List<PriceDataDTO> jpaViewPriceDataHighToDTO(List<ViewPriceDataHigh> viewPriceDataHighList);

    List<TblPriceDataHigh> dtoToJpaTblPriceDataHigh(List<PriceDataDTO> priceDataDTOList);

    List<PriceDataDTO> jpaViewPriceDataLowToDTO(List<ViewPriceDataLow> viewPriceDataLowList);

    List<TblPriceDataLow> dtoToJpaTblPriceDataLow(List<PriceDataDTO> priceDataDTOList);

    List<PriceDataDTO> jpaViewPriceDataStatsToDTO(List<ViewPriceDataStats> viewPriceDataStatsList);

    List<TblPriceDataStats> dtoToJpaTblPriceDataStats(List<PriceDataDTO> priceDataDTOList);
}
