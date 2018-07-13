package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.cards.Card;


@Entity(tableName = "Card")
public class CardEntity {

    @PrimaryKey
    @NonNull
    private String id = "0";
    private String status;
    private String createdAt;
    private String design;

    public Card parseToDataObject() {
        Card card = new Card();

        card.setId(id);
        card.setStatus(status);
        card.setDesign(design);
        card.setCreatedAt(createdAt);

        return card;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesign() {
        return this.design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

}
