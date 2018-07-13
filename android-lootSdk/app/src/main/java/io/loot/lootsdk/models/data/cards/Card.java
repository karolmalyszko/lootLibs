package io.loot.lootsdk.models.data.cards;

import java.io.Serializable;


public class Card implements Serializable {

    String id;
    String status;
    String createdAt;
    String design;

    public Card() {
        id = "";
        status = "";
        createdAt = "";
        design = "";
    }

    public Card(Card card) {
        id = card.getId();
        status = card.getStatus();
        createdAt = card.getCreatedAt();
        design = card.getDesign();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            status = "";
        }
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        if (createdAt == null) {
            createdAt = "";
        }
        this.createdAt = createdAt;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        if (design == null) {
            design = "";
        }
        this.design = design;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return id+" "+status+" "+createdAt+" "+design;
    }
}
