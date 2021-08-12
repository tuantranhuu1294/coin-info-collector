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
public class Accessory {

    private AccessoryType type;
    private Text text;
    private Text placeHolder;
    private String value;
    private String url;
    private String imageUrl;
    private ActionId actionId;
    private String altText;

    public Accessory(AccessoryType type) {
        this.type = type;
    }

    public enum AccessoryType {
        @JsonProperty("users_select")
        USERS_SELECT("users_select"),

        @JsonProperty("image")
        IMAGE("image"),

        @JsonProperty("button")
        BUTTON("button");

        private String type;

        AccessoryType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum ActionId {
        @JsonProperty("users_select-action")
        USERS_SELECT_ACTION("users_select-action"),

        @JsonProperty("button-action")
        BUTTON_ACTION("button-action");

        private String actionId;

        ActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getActionId() {
            return actionId;
        }
    }
}
