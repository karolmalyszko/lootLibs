package io.loot.lootsdk.models.data.contacts;


import com.google.gson.annotations.SerializedName;

public class SyncedContactInfo extends Contact {
    private String phonebookId;

    public SyncedContactInfo() {
        super();
        setType(DetailsType.LOOT_PHONEBOOK);
    }

    public String getPhonebookId() {
        return phonebookId;
    }

    public void setPhonebookId(String phonebookId) {
        if (phonebookId == null) {
            phonebookId = "";
        }
        this.phonebookId = phonebookId;
    }
}
