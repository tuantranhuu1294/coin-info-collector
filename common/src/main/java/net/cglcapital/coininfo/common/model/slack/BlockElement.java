package net.cglcapital.coininfo.common.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BlockElement {

    protected BlockElementType type;

    public enum BlockElementType {
        @JsonProperty("section")
        SECTION("section"),

        @JsonProperty("image")
        IMAGE("image"),

        @JsonProperty("header")
        HEADER("header"),

        @JsonProperty("divider")
        DIVIDER("divider");

        private String type;

        BlockElementType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
