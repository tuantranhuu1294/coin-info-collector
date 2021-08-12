package net.cglcapital.coininfo.common.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeaderBlockElement extends BlockElement {

    private Text text;

    public HeaderBlockElement() {
        this.type = BlockElementType.HEADER;
    }

    public HeaderBlockElement(Text text) {
        this();
        this.text = text;
    }
}
