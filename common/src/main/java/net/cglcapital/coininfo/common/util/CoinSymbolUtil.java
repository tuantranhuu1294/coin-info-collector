package net.cglcapital.coininfo.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class CoinSymbolUtil {

    private static final String DEFAULT_PAIR_COIN_USDT_TEMP = "%sUSDT";

    public static String buildUsdtCoinPairSymbol(String coinCode) {
        Objects.requireNonNull(coinCode);
        return String.format(DEFAULT_PAIR_COIN_USDT_TEMP, coinCode);
    }

    public static final String USDT = "USDT";

    public static String extractCoinCode(String symbol) {
        if (StringUtils.isEmpty(symbol)) return symbol;

        return symbol.substring(0, symbol.length() - USDT.length());
    }
}
