package net.cglcapital.coininfo.common.model.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCCoinPriceHistoryResponse {

    private Status status;
    private JsonNode data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Status {
        private String timestamp;
        private Integer errorCode;
        private String errorMessage;
        private Integer elapsed;
        private Integer creditCount;
    }
}
