package net.cglcapital.coininfo.common.db.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class CoinDTO implements Serializable {

    private String code;

    private String name;

    private String cmcUrlSymbol;

    private StatisticDTO statistic;
}
