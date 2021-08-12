package net.cglcapital.coininfo.service.thirdparty;

import net.cglcapital.coininfo.common.model.coinmarketcap.CoinCMCStatistic;
import net.cglcapital.coininfo.service.CoinCrawler;
import net.cglcapital.coininfo.service.CoinCrawlerImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.IOException;

public class TestJsoup {

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:86.0) Gecko/20100101 Firefox/86.0";

    public static void main(String[] args) throws IOException {
        /*String link = "/currencies/open-platform/";
        System.out.println(link.substring(link.indexOf("/", 1) + 1, link.length() - 1));*/
        CoinCrawler coinCrawler = new CoinCrawlerImpl();
        CoinCMCStatistic coinCoinCMCStatistic = coinCrawler.getCoinPriceStatistic("bch", "bitcoin-cash");

    }

    private static Pair<String, String> extractCoinSymbol(Node coinNode) {
        TextNode coinNameNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(0).childNode(0).childNode(1).childNode(0).childNode(0);
        TextNode coinCodeNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(0).childNode(0).childNode(1).childNode(1).childNode(1).childNode(0);
        String link = coinNode.childNode(2).childNode(0).childNode(0).attr("href");
        System.out.println("LINK: " + link);

        return Pair.of(coinNameNode.text(), coinCodeNode.text());
    }

    private static Pair<String, String> extractCoinSymbol2(Node coinNode) {
        TextNode coinNameNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(1).childNode(0);
        TextNode coinCodeNode = (TextNode) coinNode.childNode(2).childNode(0).childNode(2).childNode(0);

        String link = coinNode.childNode(2).childNode(0).attr("href");
        System.out.println("LINK: " + link.substring(link.indexOf("/", 1), link.length() - 1));

        return Pair.of(coinNameNode.text(), coinCodeNode.text());
    }

}
