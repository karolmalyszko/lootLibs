package io.loot.lootsdk.models.networking.cards;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.io.Serializable;

import io.loot.lootsdk.models.data.cards.Card;
import io.loot.lootsdk.models.orm.CardEntity;
import lombok.Data;

@Data
public class CardResponse implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("status")
    String status;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("design")
    String design;

    public String getCreatedAt() {
        if (createdAt == null || createdAt.isEmpty()) {
            return "";
        }

        DateTime dateTime = new DateTime(createdAt);

        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();

        // Formatting to get proper style of numbers
        String dayString = String.valueOf(day);
        String monthString = String.valueOf(month);
        year = year - 2000;

        if (dayString.length() == 1) {
            dayString = '0' + dayString;
        }

        if (monthString.length() == 1) {
            monthString = '0' + monthString;
        }

        return dayString + "/" + monthString + "/" + year;
    }

    public static Card parseToDataObject(CardResponse cardResponse) {
        if (cardResponse == null) {
            cardResponse = new CardResponse();
        }

        Card card = new Card();

        card.setId(cardResponse.getId());
        card.setStatus(cardResponse.getStatus());
        card.setDesign(cardResponse.getDesign());
        card.setCreatedAt(cardResponse.getCreatedAt());

        return card;
    }

    public static CardEntity parseToEntityObject(CardResponse cardResponse) {
        if (cardResponse == null) {
            cardResponse = new CardResponse();
        }

        CardEntity cardEntity = new CardEntity();

        cardEntity.setId(cardResponse.getId());
        cardEntity.setStatus(cardResponse.getStatus());
        cardEntity.setDesign(cardResponse.getDesign());
        cardEntity.setCreatedAt(cardResponse.getCreatedAt());

        return cardEntity;
    }
}
