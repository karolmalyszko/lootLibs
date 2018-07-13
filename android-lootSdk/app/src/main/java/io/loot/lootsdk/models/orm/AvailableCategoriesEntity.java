package io.loot.lootsdk.models.orm;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.category.CategoryIcon;
import io.loot.lootsdk.utils.GreenConverter;

@Entity
public class AvailableCategoriesEntity {
    @Id
    private String id;
    @Convert(converter = GreenConverter.class, columnType = String.class)
    private List<String> changableTo;


    @Generated(hash = 1515383378)
    public AvailableCategoriesEntity(String id, List<String> changableTo) {
        this.id = id;
        this.changableTo = changableTo;
    }

    @Generated(hash = 538220413)
    public AvailableCategoriesEntity() {
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getChangableTo() {
        return changableTo;
    }

    public void setChangableTo(List<String> changableTo) {
        this.changableTo = changableTo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true; //if both pointing towards same object on heap

        AvailableCategoriesEntity a = (AvailableCategoriesEntity) obj;
        return this.id.equals(a.getId());
    }
}
