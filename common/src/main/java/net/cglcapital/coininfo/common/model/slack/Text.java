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
public class Text {

    private TextType type;
    private String text;
    private Boolean emoji;

    public enum TextType {
        @JsonProperty("plain_text")
        PLAIN_TEXT("plain_text"),

        @JsonProperty("mrkdwn")
        MRKDWN("mrkdwn");

        private String type;

        TextType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
