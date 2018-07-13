package io.loot.lootsdk.models.networking.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.category.CategoryIcon;
import lombok.Data;

@Data
public class CategoryIconResponse implements Serializable {

    @SerializedName("icon_svg_url")
    String svgUrl;

    @SerializedName("icon_png_url")
    String pngUrl;

    public static CategoryIcon parseToDataObject(CategoryIconResponse categoryIconResponse) {
        if (categoryIconResponse == null) {
            categoryIconResponse = new CategoryIconResponse();
        }
        CategoryIcon categoryIcon = new CategoryIcon(categoryIconResponse.svgUrl, categoryIconResponse.pngUrl);
        return categoryIcon;
    }

}
