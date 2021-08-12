package net.cglcapital.coininfo.service;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinCMCStatistic;
import net.cglcapital.coininfo.common.model.coinmarketcap.CoinName;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CoinCrawlerImpl implements CoinCrawler {

    @Value("${crawler.coinmarketcap.url.coin-price-history}")
    private String cmcPriceHistoryUrl;

    @Value("${crawler.coinmarketcap.url.list-coins}")
    private String cmcListCoinUrl;

    @Value("${crawler.default-user-agent}")
    private String defaultUserAgent;

    @Value("${crawler.coinmarketcap.coin-detail.statistic-elem-class-name}")
    private String cmcStatisticElemClassName;

    @Value("${crawler.coinmarketcap.coin-detail.top-market-cap-elem-class-name}")
    private String cmcTopMarketCapElemClassName;

    @Override
    public CoinCMCStatistic getCoinPriceStatistic(String coinName, String cmcUrlSymbol) {
        Document doc = null;
        try {
            String url = String.format(cmcPriceHistoryUrl, cmcUrlSymbol);
            doc = Jsoup.connect(url)
                .userAgent(defaultUserAgent)
                .get();
        } catch (IOException e) {
            log.error("Error when fetching document from CoinMarketCap", e);
        }

        if (doc == null) return null;

        return extractPriceHistory(doc, coinName);
    }

    private static final int MAX_PAGE_NUM = 3;

    @Override
    public List<CoinName> getTopMarketCap() {
        try {
            List<CoinName> result = new ArrayList<>();
            for (int page = 1; page <= MAX_PAGE_NUM; page++) {
                Document doc = Jsoup.connect(cmcListCoinUrl + page)
                    .userAgent(defaultUserAgent)
                    .get();

                Elements elems = doc.getElementsByClass(cmcTopMarketCapElemClassName);
                Element table = elems.get(0);
                List<Node> coinNodes = table.childNode(2).childNodes();
                for (int i = 0; i < coinNodes.size(); i++) {
                    if (i == 10) continue;

                    CoinName coinName;
                    if (i < 10) {
                        coinName = extractCoinSymbol(coinNodes.get(i));
                    } else {
                        coinName = extractCoinSymbol2(coinNodes.get(i));
                    }
                    result.add(coinName);
                }
            }

            return result;
        } catch (Exception e) {
            log.error("Error when crawling list of coins", e);
            return new ArrayList<>();
        }
    }

    private CoinName extractCoinSymbol(Node coinNode) {
        TextNode coinNameNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(0).childNode(0).childNode(1)
            .childNode(0).childNode(0);
        TextNode coinCodeNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(0).childNode(0).childNode(1)
            .childNode(1).childNode(1).childNode(0);
        String link = coinNode.childNode(2).childNode(0).childNode(0).attr("href");
        String cmcUrlSymbol = extractCMCUrlSymbol(link);

        return new CoinName(coinCodeNode.text(), coinNameNode.text(), cmcUrlSymbol);
    }

    private String extractCMCUrlSymbol(String link) {
        return link.substring(link.indexOf("/", 1) + 1, link.length() - 1);
    }

    private CoinName extractCoinSymbol2(Node coinNode) {
        TextNode coinNameNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(1).childNode(0);
        TextNode coinCodeNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(2).childNode(0);

        String link = coinNode.childNode(2).childNode(0).attr("href");
        String cmcUrlSymbol = extractCMCUrlSymbol(link);

        return new CoinName(coinCodeNode.text(), coinNameNode.text(), cmcUrlSymbol);
    }


    private CoinCMCStatistic extractPriceHistory(Document doc, String coinName) {
        try {
            CoinCMCStatistic coinCMCStatistic = new CoinCMCStatistic();
            Elements elements = doc.getElementsByClass(cmcStatisticElemClassName);

            // extract market rank
            Element priceTodayElem = elements.get(0);
            Integer marketRank = extractMarketRank(priceTodayElem);
            coinCMCStatistic.setMarketRank(marketRank);
            log.debug("{} Market rank: {}", coinName, marketRank);

            // extract market cap
            Element marketCapElem = elements.get(1);
            BigDecimal marketCap = extractMarketCap(marketCapElem);
            coinCMCStatistic.setMarketCap(marketCap);
            log.debug("{} Market cap: {}", coinName, marketCap);

            // extract price history
            Element priceHistoryElem = elements.get(3);
            List<Node> statisticNodes = priceHistoryElem.childNode(0).childNode(1).childNodes();

            // extract seven days low/high price
            Pair<Float, Float> sevenDayPrice = getLowAndHighPrice(statisticNodes.get(0));
            coinCMCStatistic.setSevenDayLow(sevenDayPrice.getLeft());
            coinCMCStatistic.setSevenDayHigh(sevenDayPrice.getRight());
            log.debug("{} Seven days low/high price: {}", coinName, sevenDayPrice);

            // extract thirty days low/high price
            Pair<Float, Float> thirtyDayPrice = getLowAndHighPrice(statisticNodes.get(1));
            coinCMCStatistic.setThirtyDayLow(thirtyDayPrice.getLeft());
            coinCMCStatistic.setThirtyDayHigh(thirtyDayPrice.getRight());
            log.debug("{} Thirty days low/high price: {}", coinName, thirtyDayPrice);

            // extract ninety days low/high price
            Pair<Float, Float> ninetyDayPrice = getLowAndHighPrice(statisticNodes.get(2));
            coinCMCStatistic.setNinetyDayLow(ninetyDayPrice.getLeft());
            coinCMCStatistic.setNinetyDayHigh(ninetyDayPrice.getRight());
            log.debug("{} Ninety days low/high price: {}", coinName, ninetyDayPrice);

            // extract fifty two weeks low/high price
            Pair<Float, Float> fiftyTwoWeekPrice = getLowAndHighPrice(statisticNodes.get(3));
            coinCMCStatistic.setFiftyTwoWeekLow(fiftyTwoWeekPrice.getLeft());
            coinCMCStatistic.setFiftyTwoWeekHigh(fiftyTwoWeekPrice.getRight());
            log.debug("{} Fifty two weeks low/high price: {}", coinName, fiftyTwoWeekPrice);

            // extract all time high price
            Float allTimeHighPrice = getAllTimePrice(statisticNodes.get(4));
            coinCMCStatistic.setAllTimeHigh(allTimeHighPrice);
            log.debug("{} All time high price: {}", coinName, allTimeHighPrice);

            // extract all time low price
            Float allTimeLowPrice = getAllTimePrice(statisticNodes.get(5));
            coinCMCStatistic.setAllTimeLow(allTimeLowPrice);
            log.debug("{} All time low price: {}", coinName, allTimeLowPrice);

            return coinCMCStatistic;
        } catch (Exception e) {
            log.error("Error when extracting crawled data", e);
            return null;
        }
    }

    private BigDecimal extractMarketCap(Element element) {
        try {
            TextNode textNode = (TextNode) element.childNode(0).childNode(1).childNode(0).childNode(1).childNode(0).childNode(0);
            String marketCap = textNode.text().replace("$", "").replace(",", "");
            return new BigDecimal(marketCap);
        } catch (Exception e) {
            log.warn("Error when extracting market cap", e);
            return null;
        }
    }

    private Integer extractMarketRank(Element element) {
        try {
            TextNode marketRankNode = (TextNode) ((Element) ((element.childNode(0)).childNode(1)))
                .getElementsContainingOwnText("#").get(0).childNode(0);

            return Integer.parseInt(marketRankNode.text().substring(1));
        } catch (Exception e) {
            log.warn("Error when extracting market rank", e);
            return null;
        }
    }

    private static Pair<Float, Float> getLowAndHighPrice(Node node) {
        try {
            TextNode lowTextNode = (TextNode) node.childNode(1).childNode(0).childNode(0);
            TextNode highTextNode = (TextNode) node.childNode(1).childNode(1).childNode(0);
            String lowPrice = lowTextNode.getWholeText().replace("$", "").replace(",", "");
            String highPrice = highTextNode.getWholeText().replace("$", "").replace(",", "");
            return Pair.of(Float.parseFloat(lowPrice), Float.parseFloat(highPrice));
        } catch (Exception e) {
            log.warn("Error when extracting low/high price", e);
            return Pair.of(null, null);
        }
    }

    private static Float getAllTimePrice(Node node) {
        try {
            TextNode textNode = (TextNode) node.childNode(1).childNode(0).childNode(0);
            String price = textNode.getWholeText().replace("$", "").replace(",", "");
            return Float.parseFloat(price);
        } catch (Exception e) {
            log.warn("Error when extracting all time price", e);
            return null;
        }
    }
}
