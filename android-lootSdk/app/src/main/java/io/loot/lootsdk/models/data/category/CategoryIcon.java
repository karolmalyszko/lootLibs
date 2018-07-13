package io.loot.lootsdk.models.data.category;


import java.io.Serializable;

public class CategoryIcon implements Serializable {

    private String svgUrl;
    private String pngUrl;

    public CategoryIcon() {
        svgUrl = "";
        pngUrl = "";
    }

    public CategoryIcon(CategoryIcon categoryIcon) {
        svgUrl = categoryIcon.getSvgUrl();
        pngUrl = categoryIcon.getPngUrl();
    }

    public CategoryIcon(String svgUrl, String pngUrl) {
        if (svgUrl == null) {
            svgUrl = "";
        }
        this.svgUrl = svgUrl;
        if (pngUrl == null) {
            pngUrl = "";
        }
        this.pngUrl = pngUrl;
    }

    public String getSvgUrl() {
        return svgUrl;
    }

    public void setSvgUrl(String svgUrl) {
        if (svgUrl == null) {
            svgUrl = "";
        }
        this.svgUrl = svgUrl;
    }

    public String getPngUrl() {
        return pngUrl;
    }

    public void setPngUrl(String pngUrl) {
        if (pngUrl == null) {
            pngUrl = "";
        }
        this.pngUrl = pngUrl;
    }
}
