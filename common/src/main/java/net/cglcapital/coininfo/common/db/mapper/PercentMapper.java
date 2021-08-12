package net.cglcapital.coininfo.common.db.mapper;

import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PercentMapper {

    PercentMapper MAPPER = Mappers.getMapper(PercentMapper.class);

    List<PercentDTO> jpaViewPercentConsolidationToDTO(List<ViewPercentConsolidation> viewPercentConsolidations);

    List<TblPercentConsolidation> dtoToJpaTblPercentConsolidation(List<PercentDTO> percentDTOList);

    List<PercentDTO> jpaViewPercentFailedBreakoutToDTO(List<ViewPercentFailedBreakout> viewPercentFailedBreakouts);

    List<TblPercentFailedBreakout> dtoToJpaTblPercentFailedBreakout(List<PercentDTO> percentDTOList);

    List<PercentDTO> jpaViewPercentTrueBreakoutToDTO(List<ViewPercentTrueBreakout> viewPercentTrueBreakouts);

    List<TblPercentTrueBreakout> dtoToJpaTblPercentTrueBreakout(List<PercentDTO> percentDTOList);

    List<PercentDTO> jpaViewPercentTrueBreakoutFibToDTO(List<ViewPercentTrueBreakoutFib> viewPercentTrueBreakoutFibs);

    List<TblPercentTrueBreakoutFib> dtoToJpaTblPercentTrueBreakoutFib(List<PercentDTO> percentDTOList);
}
