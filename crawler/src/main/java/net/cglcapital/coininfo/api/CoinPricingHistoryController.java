package net.cglcapital.coininfo.api;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.BaseController;
import net.cglcapital.coininfo.service.CoinPriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class CoinPricingHistoryController extends BaseController {

    @Autowired
    private CoinPriceHistoryService coinPriceHistoryService;

    /*@GetMapping("/coins/{coinName}/price-history")
    public ResponseEntity<?> getCoinPriceHistory(@PathVariable String coinName) {
        return ok(coinPriceHistoryService.getCoinPriceHistory(coinName));
    }*/

    @GetMapping("/coins/crawl-top-coins")
    public ResponseEntity<?> crawlTopCoins() {
        coinPriceHistoryService.getListCoins();
        return ok();
    }
}
