package net.cglcapital.coininfo.schedule;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.db.dao.*;
import net.cglcapital.coininfo.common.db.domain.dto.PercentDTO;
import net.cglcapital.coininfo.common.db.domain.dto.PriceDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class CalculatePercentJob {

    @Autowired
    private PercentConsolidationDao percentConsolidationDao;

    @Autowired
    private PercentFailedBreakoutDao percentFailedBreakoutDao;

    @Autowired
    private PercentTrueBreakoutDao percentTrueBreakoutDao;

    @Autowired
    private PercentTrueBreakoutFibDao percentTrueBreakoutFibDao;

    @Autowired
    private PriceDataHighDao priceDataHighDao;

    @Autowired
    private PriceDataLowDao priceDataLowDao;

    @Autowired
    private PriceDataStatsDao priceDataStatsDao;

    @Scheduled(cron = "${cronjob.calculate-percent.cron-expression}", zone = "${cronjob.calculate-percent.zone}") // schedule daily at a specific time
    public void calculatePercent() {
        log.info("Started Calculate Percent job");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Callable<String>> callableList = new ArrayList<>();
        callableList.add(() -> {
            percentConsolidationDao.deleteAllDataTbl();
            List<PercentDTO> dataView = percentConsolidationDao.findAllView();
            percentConsolidationDao.saveAllTbl(dataView);
            return percentConsolidationDao.getClass().getSimpleName();
        });

        callableList.add(() -> {
            percentFailedBreakoutDao.deleteAllDataTbl();
            List<PercentDTO> dataView = percentFailedBreakoutDao.findAllView();
            percentFailedBreakoutDao.saveAllTbl(dataView);
            return percentFailedBreakoutDao.getClass().getSimpleName();
        });

        callableList.add(() -> {
            percentTrueBreakoutDao.deleteAllDataTbl();
            List<PercentDTO> dataView = percentTrueBreakoutDao.findAllView();
            percentTrueBreakoutDao.saveAllTbl(dataView);
            return percentTrueBreakoutDao.getClass().getSimpleName();
        });

        callableList.add(() -> {
            priceDataHighDao.deleteAllDataTbl();
            List<PriceDataDTO> dataView = priceDataHighDao.findAllView();
            priceDataHighDao.saveAllTbl(dataView);
            return priceDataHighDao.getClass().getSimpleName();
        });

        callableList.add(() -> {
            priceDataLowDao.deleteAllDataTbl();
            List<PriceDataDTO> dataView = priceDataLowDao.findAllView();
            priceDataLowDao.saveAllTbl(dataView);
            return priceDataLowDao.getClass().getSimpleName();
        });

        callableList.add(() -> {
            priceDataStatsDao.deleteAllDataTbl();
            List<PriceDataDTO> dataView = priceDataStatsDao.findAllView();
            priceDataStatsDao.saveAllTbl(dataView);
            return priceDataStatsDao.getClass().getSimpleName();
        });

        try {
            executor.invokeAll(callableList);
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        percentTrueBreakoutFibDao.deleteAllDataTbl();
        List<PercentDTO> dataView = percentTrueBreakoutFibDao.findAllView();
        percentTrueBreakoutFibDao.saveAllTbl(dataView);
        log.info("Finished Calculate Percent job");
    }

}
