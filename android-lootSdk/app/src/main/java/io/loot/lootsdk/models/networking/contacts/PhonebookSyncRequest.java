package io.loot.lootsdk.models.networking.contacts;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PhonebookSyncRequest {
    @SerializedName("phonebook")
    private ArrayList<PhonebookSyncRequestItem> phoneNumbers;

    public PhonebookSyncRequest() {
        this.phoneNumbers = new ArrayList<>();
    }
}
