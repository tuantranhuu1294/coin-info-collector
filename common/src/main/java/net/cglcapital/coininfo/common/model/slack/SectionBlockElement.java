package net.cglcapital.coininfo.common.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionBlockElement extends BlockElement {

    private Text text;
    private List<Text> fields;
    private Accessory accessory;

    public SectionBlockElement() {
        this.type = BlockElementType.SECTION;
    }

}
