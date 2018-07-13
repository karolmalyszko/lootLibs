package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class ContactPreferences implements Serializable {
    private ContactPreference contactPreference;

    public ContactPreference getContactPreference() {
        return contactPreference;
    }

    public void setContactPreference(ContactPreference contactPreference) {
        this.contactPreference = contactPreference;
    }
}
