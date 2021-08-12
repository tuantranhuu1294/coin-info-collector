package net.cglcapital.coininfo.common.model.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoinName {

    private String code;
    private String name;
    private String cmcUrlSymbol;
}
