package net.cglcapital.coininfo.common.db.dao;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.StatisticDTO;
import net.cglcapital.coininfo.common.db.domain.jpa.Coin;
import net.cglcapital.coininfo.common.db.domain.jpa.Statistic;
import net.cglcapital.coininfo.common.db.repository.CoinRepository;
import net.cglcapital.coininfo.common.db.mapper.CoinMapper;
import net.cglcapital.coininfo.common.db.mapper.StatisticMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
public class CoinInfoDaoImpl implements CoinInfoDao {

    @Resource
    private CoinRepository coinRepository;

    @Override
    public List<CoinDTO> findAll() {
        List<Coin> listCoins = coinRepository.findAll();
        return CoinMapper.MAPPER.coinsToCoinDTOs(listCoins);
    }

    @Override
    public List<CoinDTO> findAll(Sort sort) {
        List<Coin> listCoins = coinRepository.findAll(sort);
        return CoinMapper.MAPPER.coinsToCoinDTOs(listCoins);
    }

    @Cacheable(value = "coinInfo", unless = "#result == null", key = "#coinCode")
    @Override
    public CoinDTO getByCode(String coinCode) {
        Coin coin = coinRepository.findById(coinCode).orElse(null);
        if (coin == null) {
            log.info("Coin {} not found", coinCode);
            return null;
        }

        return CoinMapper.MAPPER.coinToCoinDTO(coin);
    }

    @Transactional
    @Override
    public CoinDTO save(CoinDTO coinDTO) {
        coinRepository.save(CoinMapper.MAPPER.coinDTOToCoin(coinDTO));
        return coinDTO;
    }

    @Transactional
    @Override
    public void saveAll(List<CoinDTO> coinDTOList) {
        List<Coin> coinList = CoinMapper.MAPPER.coinDTOsToCoins(coinDTOList);
        coinRepository.saveAll(coinList);
        log.info("Saved: {} coins to database", coinDTOList.size());
    }


    @Transactional
    @Override
    public void update(CoinDTO coinDTO) {
        Coin coin = coinRepository.findById(coinDTO.getCode()).orElse(null);
        if (coin == null) {
            log.warn("Coin: {} not found", coinDTO.getCode());
            return;
        }

        Optional.ofNullable(coinDTO.getStatistic()).ifPresent(statisticDTO -> updateStatisticField(coin, statisticDTO));

    }

    private void updateStatisticField(Coin coin, StatisticDTO statisticDTO) {
        if (statisticDTO == null) return;

        Statistic statistic = coin.getStatistic();
        if (statistic == null) {
            statistic = StatisticMapper.MAPPER.statisticDTOToStatistic(statisticDTO);
            statistic.setCoin(coin);
            coin.setStatistic(statistic);
            return;
        }

        Optional.ofNullable(statisticDTO.getMarketCap()).ifPresent(statistic::setMarketCap);
        Optional.ofNullable(statisticDTO.getMarketRank()).ifPresent(statistic::setMarketRank);
        Optional.ofNullable(statisticDTO.getTwentyFourHourHigh()).ifPresent(statistic::setTwentyFourHourHigh);
        Optional.ofNullable(statisticDTO.getTwentyFourHourLow()).ifPresent(statistic::setTwentyFourHourLow);
        Optional.ofNullable(statisticDTO.getSevenDayHigh()).ifPresent(statistic::setSevenDayHigh);
        Optional.ofNullable(statisticDTO.getSevenDayLow()).ifPresent(statistic::setSevenDayLow);
        Optional.ofNullable(statisticDTO.getFiftyTwoWeekHigh()).ifPresent(statistic::setFiftyTwoWeekHigh);
        Optional.ofNullable(statisticDTO.getFiftyTwoWeekLow()).ifPresent(statistic::setFiftyTwoWeekLow);
        Optional.ofNullable(statisticDTO.getThirtyDayHigh()).ifPresent(statistic::setThirtyDayHigh);
        Optional.ofNullable(statisticDTO.getThirtyDayLow()).ifPresent(statistic::setThirtyDayLow);
        Optional.ofNullable(statisticDTO.getNinetyDayHigh()).ifPresent(statistic::setNinetyDayHigh);
        Optional.ofNullable(statisticDTO.getNinetyDayLow()).ifPresent(statistic::setNinetyDayLow);
        Optional.ofNullable(statisticDTO.getAllTimeHigh()).ifPresent(statistic::setAllTimeHigh);
        Optional.ofNullable(statisticDTO.getAllTimeLow()).ifPresent(statistic::setAllTimeLow);
    }
}
