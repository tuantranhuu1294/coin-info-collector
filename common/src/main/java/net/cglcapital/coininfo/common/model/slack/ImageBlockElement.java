package net.cglcapital.coininfo.common.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageBlockElement extends BlockElement {

    private Text title;
    private String imageUrl;
    private String altText;

    public ImageBlockElement() {
        this.type = BlockElementType.IMAGE;
    }

    public ImageBlockElement(Text title, String imageUrl, String altText) {
        this();
        this.title = title;
        this.imageUrl = imageUrl;
        this.altText = altText;
    }
}
