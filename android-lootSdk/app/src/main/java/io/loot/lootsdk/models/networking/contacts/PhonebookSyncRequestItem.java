package io.loot.lootsdk.models.networking.contacts;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PhonebookSyncRequestItem {
    @SerializedName("phonebook_id")
    private String phonebookId;
    @SerializedName("name")
    private String name;
    @SerializedName("phone_numbers")
    private ArrayList<String> phoneNumbers;
    private transient String profilePhoto;
}
